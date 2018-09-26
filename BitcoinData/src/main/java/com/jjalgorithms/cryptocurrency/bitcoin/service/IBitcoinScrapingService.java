package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;

public interface IBitcoinScrapingService {
	
	public List<BitcoinData> scrape(Long startDate, Long endDate);
	
	public boolean startAutomatedScraping();

	public boolean stopAutomatedScraping();
	
	public boolean isScraping();
	
}
