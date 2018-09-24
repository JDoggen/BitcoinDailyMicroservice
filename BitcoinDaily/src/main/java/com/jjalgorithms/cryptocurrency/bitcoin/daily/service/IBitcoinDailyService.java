package com.jjalgorithms.cryptocurrency.bitcoin.daily.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.daily.model.BitcoinDaily;

public interface IBitcoinDailyService {
	
	public long count();
	
	public List<BitcoinDaily> findAll();
	
	public <S extends BitcoinDaily> S save(S s);
	
	public <S extends BitcoinDaily> List<S> saveAll(Iterable<S> s);
	
	public BitcoinDaily getLastDay();
	
	public List<BitcoinDaily> scrape(Long startDate, Long endDate);
	
	public boolean startAutomatedScraping();
	
	public boolean endAutomatedScraping();
	
	public List<BitcoinDaily> findByTimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	public boolean isScraping();
	
	public String firstEntry();
	
}
