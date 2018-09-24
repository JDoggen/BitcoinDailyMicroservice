package com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.model.Bitcoin6Hourly;

public interface IBitcoin6HourlyService {
	
	public long count();
	
	public List<Bitcoin6Hourly> findAll();
	
	public <S extends Bitcoin6Hourly> S save(S s);
	
	public <S extends Bitcoin6Hourly> List<S> saveAll(Iterable<S> s);
	
	public Bitcoin6Hourly getLast6Hours();
	
	public List<Bitcoin6Hourly> scrape(Long startDate, Long endDate);
	
	public boolean startAutomatedScraping();
	
	public boolean endAutomatedScraping();
	
	public List<Bitcoin6Hourly> findByTimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	public boolean isScraping();
	
	public String firstEntry();
	
}
