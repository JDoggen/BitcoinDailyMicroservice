package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;
import java.util.Optional;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;

public interface IPredictionService {
	
	public long count();
	
	public Prediction createPrediction(Long timeStampStart, Long timeStampEnd);
	
	public Prediction changePrediction(Long id, Long timeStampStart, Long timeStampEnd);
	
	public List<Prediction> findAll();
	
	public Optional <Prediction> findById(Long id);
		
	public void deleteById(Long id);

	
}
