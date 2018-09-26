package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.ArrayList;
import java.util.List;

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
	
	public Prediction getPrediction(Long timeStampStart, Long timeStampEnd) { 
		Prediction prediction = new Prediction();
		prediction.setBitcoindata(this.iBitcoinDataDao.findByTimeStampBetween(timeStampStart, timeStampEnd));
		List <Double> closeValues = getCloseValuesBytimeStampBetween(prediction.getBitcoindata());
		Double averageClose = getCloseValuesAverageBetween(closeValues);
		prediction.setOneDayPrediction(averageClose);
		prediction.setSevenDayPrediction(0.0);
		this.iPredictionDao.save(prediction);
		return prediction;
		
	}

	public Double getCloseValuesAverageBetween(List <Double> list) {
		Double combinedCloseValues = 0.0;
		
		for(Double x: list)
			combinedCloseValues += x;
		
		return combinedCloseValues/list.size();
	}
	
	public List<Double> getCloseValuesBytimeStampBetween(List<BitcoinData> list) {
		ArrayList <Double> y = new ArrayList<>();
		for(BitcoinData x: list) {
			y.add(x.getClose());
		}
		return y;	
	}
	
	@Override
	public List<Prediction> findAll(){
		return this.iPredictionDao.findAll();
	}
	
	public Double create() {
		return 0.0;
	}
	public void deleteById(Long id) {
		this.iPredictionDao.deleteById(id);
	}

}
