package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IBitcoinDataDao;
import com.jjalgorithms.cryptocurrency.bitcoin.dao.IPredictionDao;
import com.jjalgorithms.cryptocurrency.bitcoin.dao.IUserDao;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.UserDto;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;


@Service
public class PredictionService implements IPredictionService{
	
	@Autowired
	private IPredictionDao iPredictionDao;
	@Autowired
	private IBitcoinDataDao iBitcoinDataDao;

	@Autowired
	private IUserDao iUserDao;
	
	@Override
	public long count() {
		return this.iPredictionDao.count();
	}
	
	@Override
	public List<Prediction> getUserPrediction(UserDto userDto){
		User user = this.iUserDao.findByUserName(userDto.getUserName()).get();
		ArrayList<Prediction> unsortedPredictions = new ArrayList<>();
		unsortedPredictions.addAll(user.getPrediction());
		List<Prediction> sortedPredictions = new ArrayList<Prediction>();
		int size = unsortedPredictions.size();
		for(int i = 0; i < size; i++) {
			int index = 0;
			Long idOfIndex = unsortedPredictions.get(0).getId(); 
			for(int j = 0; j < unsortedPredictions.size(); j ++) {
				if(unsortedPredictions.get(j).getId() < idOfIndex) {
					index = j;
					idOfIndex = unsortedPredictions.get(j).getId();
				}
			}
			sortedPredictions.add(unsortedPredictions.remove(index));
		}
		return sortedPredictions;
	}
	
	@Override
	public User createPrediction(User user) { 
		Prediction prediction = user.getPrediction().get(0);
		prediction.setBitcoindata(getPredictionBitcoinData(prediction.getStart(), prediction.getEnd()));
		List <Double> closeValues = getCloseValuesBytimeStampBetween(prediction.getBitcoindata());
		prediction.setAverageCloseValue(getCloseValuesAverageBetween(closeValues));
		prediction.setLastCloseValue(closeValues.get(closeValues.size()-1));	
		prediction.setTheFactor(calculateTheFactor(closeValues));
		prediction.setStandardDeviation(calculateStandardDeviation(closeValues, prediction.getAverageCloseValue()));
		prediction.setOneDayPrediction(prediction.getTheFactor()*prediction.getLastCloseValue()); 
		user = this.iUserDao.findByUserName(user.getUserName()).get();
		if(user.getPrediction() == null) {
			user.setPrediction(new ArrayList<>());
		}
		user.getPrediction().add(prediction);
		this.iUserDao.save(user);
		return user;
	}
	
	public List<BitcoinData> getPredictionBitcoinData(Long timeStampStart, Long timeStampEnd){
		return this.iBitcoinDataDao.findByTimeStampBetweenOrderByTimeStampAsc(timeStampStart, timeStampEnd);
	}
	
	public Double calculateTheFactor(List <Double> listOfCloseValues)	{					//prediction based on percentage change
		ArrayList <Double> percentages = new ArrayList<>();
		for(int x = 1; x < listOfCloseValues.size() ;x++) {
				percentages.add((listOfCloseValues.get(x)/listOfCloseValues.get(x-1))-1);
			}
		
		Double totalFactors = 0.0;
		for (Double factor: percentages) {
			totalFactors +=factor;
		}
		
		Double theFactor = ((totalFactors/percentages.size())*100)+1;
		return theFactor;
	}

	public Double getCloseValuesAverageBetween(List <Double> listOfCloseValues) {
		Double combinedCloseValues = 0.0;
		
		for(Double closeValue : listOfCloseValues)
			combinedCloseValues += closeValue;
		
		return combinedCloseValues/listOfCloseValues.size();
	}
	
	public List<Double> getCloseValuesBytimeStampBetween(List<BitcoinData> listOfBitcoinData) {
		ArrayList <Double> listOfCloseValues = new ArrayList<>();
		for(BitcoinData bitcoinData: listOfBitcoinData) {
			listOfCloseValues.add(bitcoinData.getClose());
		}
		return listOfCloseValues;	
	}
	
	public ArrayList<Double> listOfDifferences(List <Double> listOfCloseValues, Double closeValueAverage){
		ArrayList<Double> listOfDifferences = new ArrayList<>();
		for(Double closeValue: listOfCloseValues) {
			listOfDifferences.add(closeValue-closeValueAverage);
		}
		return listOfDifferences;	
	}
	
	public ArrayList<Double> listOfSquares(ArrayList <Double> listOfDifferences){
		ArrayList<Double> listOfSquares = new ArrayList<>();
		for(Double difference: listOfDifferences) {
			listOfSquares.add(difference * difference);			
		}
		return listOfSquares;
	}
	
	public Double calculateAverageOfSquares(ArrayList <Double> listOfSquareRoots) {
		Double sumOfSquares = 0.0;
		for(Double squareValue : listOfSquareRoots) {
			sumOfSquares += squareValue;
		}
		return sumOfSquares/listOfSquareRoots.size();
	}
	
	public Double calculateStandardDeviation(List <Double> listOfCloseValues, Double closeValueAverage) { 
		ArrayList<Double> listOfDifferences = listOfDifferences(listOfCloseValues, closeValueAverage);
		ArrayList<Double> listOfSquares = listOfSquares(listOfDifferences);
		Double averageOfSquares = calculateAverageOfSquares(listOfSquares);
		Double standardDeviation = Math.sqrt(averageOfSquares);
		return standardDeviation;
	}
	
	@Override
	public List<Prediction> findAll(){
		return this.iPredictionDao.findAll();
	}
	
	public List<Prediction> findAllByOrderByIdAsc(){
		return this.iPredictionDao.findAllByOrderByIdAsc();
	}
	
	public void deleteById(Long id) {
		this.iPredictionDao.deleteById(id);
	}
	
	public Prediction changePrediction(Long id, Long timeStampStart, Long timeStampEnd) { 
			Prediction prediction = this.findById(id);
			prediction.setBitcoindata(getPredictionBitcoinData(timeStampStart, timeStampEnd));
			List <Double> closeValues = getCloseValuesBytimeStampBetween(prediction.getBitcoindata());
			prediction.setAverageCloseValue(getCloseValuesAverageBetween(closeValues));
			prediction.setLastCloseValue(closeValues.get(closeValues.size()-1));	
			prediction.setTheFactor(calculateTheFactor(closeValues));
			prediction.setStandardDeviation(calculateStandardDeviation(closeValues, prediction.getAverageCloseValue()));
			prediction.setOneDayPrediction((prediction.getTheFactor()*prediction.getLastCloseValue())); 
			prediction.setStart(timeStampStart);
			prediction.setEnd(timeStampEnd);
			this.iPredictionDao.save(prediction);
			return prediction;		
	}

	public Prediction findById(Long id){
		Optional <Prediction> optionalPrediction = this.iPredictionDao.findById(id);
		Assert.notNull(optionalPrediction.get(), "Couldn't find ID in database");
		return optionalPrediction.get();
	}

}
