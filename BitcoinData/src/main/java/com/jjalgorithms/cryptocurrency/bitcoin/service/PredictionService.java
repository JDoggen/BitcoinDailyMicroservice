package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IBitcoinDataDao;
import com.jjalgorithms.cryptocurrency.bitcoin.dao.IPredictionDao;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;


@Service
public class PredictionService implements IPredictionService{
	
	private IPredictionDao iPredictionDao;
	private IBitcoinDataDao iBitcoinDataDao;

	@Override
	public long count() {
		return this.iPredictionDao.count();
	}
	
	public Double setOneDayPrediction(Long timeStampStart, Long timeStampEnd) {
		return 0.0;
	}

	public Double getCloseValuesAverageBetween(Long timeStampStart, Long timeStampEnd) {
		Double combinedCloseValues = 0.0;
		for(Double x: getCloseValuesBytimeStampBetween(timeStampStart,timeStampEnd))
			combinedCloseValues += x;
		
		return combinedCloseValues/(getCloseValuesBytimeStampBetween(timeStampStart,timeStampEnd).size());
	}
	
	public List<Double> getCloseValuesBytimeStampBetween(Long timeStampStart, Long timeStampEnd) {
		List<BitcoinData> list = this.iBitcoinDataDao.findByTimeStampBetween(timeStampStart, timeStampEnd);
		ArrayList <Double> y = new ArrayList();
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

}
