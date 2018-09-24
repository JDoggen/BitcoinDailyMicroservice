package com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.model.Bitcoin6Hourly;

public interface IBitcoin6HourlyScraperService {
	
	public List<Bitcoin6Hourly> scrape(Long startDate, Long endDate);
	
	public Bitcoin6Hourly getLast6Hours();

	public boolean startAutomatedScraping();

	public boolean endAutomatedScraping();
	
	public boolean isScraping();

}
