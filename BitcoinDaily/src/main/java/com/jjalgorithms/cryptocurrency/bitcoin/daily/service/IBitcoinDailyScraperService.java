package com.jjalgorithms.cryptocurrency.bitcoin.daily.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.daily.model.BitcoinDaily;

public interface IBitcoinDailyScraperService {
	
	public List<BitcoinDaily> scrape(Long startDate, Long endDate);
	
	public BitcoinDaily getLastDay();

	public boolean startAutomatedScraping();

	public boolean endAutomatedScraping();
	
	public boolean isScraping();

}
