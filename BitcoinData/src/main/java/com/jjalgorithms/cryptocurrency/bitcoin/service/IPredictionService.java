package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;
import java.util.Optional;

import com.jjalgorithms.cryptocurrency.bitcoin.dto.UserDto;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;

public interface IPredictionService {
	
	public long count();
	
	public User createPrediction(User user);
	
	public Prediction changePrediction(Long id, Long timeStampStart, Long timeStampEnd);
	
	public List<Prediction> findAll();
	
	public Prediction findById(Long id);
		
	public void deleteById(Long id);
	
	public List<Prediction> getUserPrediction(UserDto userDto);

	
}
