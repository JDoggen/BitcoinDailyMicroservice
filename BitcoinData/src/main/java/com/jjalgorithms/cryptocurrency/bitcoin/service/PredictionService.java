package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IBitcoinDataDao;
import com.jjalgorithms.cryptocurrency.bitcoin.dao.IPredictionDao;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;


@Service
public class PredictionService implements IPredictionService{
	
	@Autowired
	private IPredictionDao iPredictionDao;
	@Autowired
	private IBitcoinDataDao iBitcoinDataDao;

	@Override
	public long count() {
		return this.iPredictionDao.count();
	}
	
	public Prediction createPrediction(Long timeStampStart, Long timeStampEnd) { 
		Prediction prediction = new Prediction();
		prediction.setBitcoindata(getPredictionBitcoinData(timeStampStart, timeStampEnd));
		List <Double> closeValues = getCloseValuesBytimeStampBetween(prediction.getBitcoindata());
		prediction.setAverageCloseValue(getCloseValuesAverageBetween(closeValues));
		prediction.setLastCloseValue(closeValues.get(closeValues.size()-1));	
		prediction.setTheFactor(calculateTheFactor(closeValues));
		prediction.setStandardDeviation(calculateStandardDeviation(closeValues, prediction.getAverageCloseValue()));
		prediction.setOneDayPrediction(prediction.getTheFactor()*prediction.getLastCloseValue()); 
		prediction.setSevenDayPrediction(0.0);
		this.iPredictionDao.save(prediction);
		return prediction;	
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
	
	public void deleteById(Long id) {
		this.iPredictionDao.deleteById(id);
	}
	
	public Prediction changePrediction(Long id, Long timeStampStart, Long timeStampEnd) { 
		Optional <Prediction> optionalPrediction = this.findById(id);
		if(optionalPrediction.isPresent()) {
			Prediction prediction = optionalPrediction.get();
			prediction.setBitcoindata(getPredictionBitcoinData(timeStampStart, timeStampEnd));
			List <Double> closeValues = getCloseValuesBytimeStampBetween(prediction.getBitcoindata());
			Double closeValueAverage = getCloseValuesAverageBetween(closeValues);
			Double lastCloseValue = closeValues.get(closeValues.size()-1);	
			prediction.setTheFactor(calculateTheFactor(closeValues));
			prediction.setStandardDeviation(calculateStandardDeviation(closeValues, closeValueAverage));
			prediction.setOneDayPrediction(prediction.getTheFactor()*lastCloseValue); 
			prediction.setSevenDayPrediction(closeValueAverage);
			this.iPredictionDao.save(prediction);
			return prediction;
		}
		else return null;	
	}

	public Optional<Prediction> findById(Long id){
		return this.iPredictionDao.findById(id);
	}

}
