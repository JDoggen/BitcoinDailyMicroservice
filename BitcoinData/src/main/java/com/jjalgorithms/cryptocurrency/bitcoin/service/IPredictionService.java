package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;

public interface IPredictionService {
	
	public long count();
	
	public List<Double> getCloseValuesBytimeStampBetween(Long timeStampStart, Long timeStampEnd);

	public Double getCloseValuesAverageBetween(Long timeStampStart, Long timeStampEnd); 
	
	public List<Prediction> findAll();
	
	public Double create();
}
