package com.jjalgorithms.cryptocurrency.bitcoin.daily.service;

import java.util.Date;
import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.daily.model.BitcoinDaily;

public interface IBitcoinDailyService {
	
	public long count();
	
	public List<Double> getAllOpen();
	
	public List<Double> getAllClose();
	
	public Double getOverallOpenAverage(); 
	
	public Double getOverallCloseAverage(); 
	
	public List<BitcoinDaily> findAll();
	
	public <S extends BitcoinDaily> S save(S s);
	
	public <S extends BitcoinDaily> List<S> saveAll(Iterable<S> s);
	
	public BitcoinDaily getLastDay();
	
	public List<BitcoinDaily> scrape(Long startDate, Long endDate);
	
	public boolean startAutomatedScraping();
	
	public boolean endAutomatedScraping();
	
	public List<BitcoinDaily> findBytimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	public String unixToDate (Long timestamp);
	
	public boolean isScraping();
	
	public String firstEntry();
	
}
