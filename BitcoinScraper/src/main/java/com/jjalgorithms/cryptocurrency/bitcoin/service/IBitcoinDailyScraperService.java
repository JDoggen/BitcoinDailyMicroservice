package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinDaily;

public interface IBitcoinDailyScraperService {
	
	public List<BitcoinDaily> scrape(Long startDate, Long endDate);

	public boolean startAutomatedScraping();

	public boolean endAutomatedScraping();

}
