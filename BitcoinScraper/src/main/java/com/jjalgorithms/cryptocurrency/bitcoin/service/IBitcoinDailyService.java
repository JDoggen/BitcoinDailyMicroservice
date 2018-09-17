package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinDaily;

public interface IBitcoinDailyService {
	
	public long count();
	
	public List<BitcoinDaily> findAll();
	
	public <S extends BitcoinDaily> S save(S s);
	
	public <S extends BitcoinDaily> List<S> saveAll(Iterable<S> s);
	
	
	
	public List<BitcoinDaily> scrape(Long startDate, Long endDate);
	
	public boolean startAutomatedScraping();
	
	public boolean endAutomatedScraping();
	
	/*
	public BitcoinDaily findBetweenTimeStamp(Long timeStampStart, Long timeStampEnd);
	*/
}
