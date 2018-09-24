package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;

public interface IBitcoinDataService {
	
	public long count();
	
	public List<Double> getAllOpen();
	
	public List<Double> getAllClose();
	
	public Double getOverallOpenAverage(); 
	
	public Double getOverallCloseAverage(); 
	
	public List<BitcoinData> findAll();
	
	public <S extends BitcoinData> S save(S s);
	
	public <S extends BitcoinData> List<S> saveAll(Iterable<S> s);
	
	public BitcoinData getLastDay();
	
	public List<BitcoinData> scrape(Long startDate, Long endDate);
	
	public boolean startAutomatedScraping();
	
	public boolean endAutomatedScraping();
	
	public List<BitcoinData> findBytimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	public String unixToDate (Long timestamp);
	
	public boolean isScraping();
	
	public String firstEntry();
	
}
