package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinDaily;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IBitcoinDailyDAO;;

@Service
public class BitcoinDailyService implements IBitcoinDailyService{
	
	@Autowired
	private IBitcoinDailyDAO iBitcoinDailyDAO;
	
	@Autowired
	public IBitcoinDailyScraperService iBitcoinDailyScraperService;
	
	
	//Methods directly accessible through the interface, used for interaction with the database
	@Override
	public long count() {
		return this.iBitcoinDailyDAO.count();
	}

	@Override
	public List<BitcoinDaily> findAll() {
		return this.iBitcoinDailyDAO.findAll();
	}
	
	@Override
	public <S extends BitcoinDaily> S save(S s) {
		Assert.notNull(s, "BitcoinDaily can't be null");
		return this.iBitcoinDailyDAO.save(s);
	}

	@Override
	public <S extends BitcoinDaily> List<S> saveAll(Iterable<S> s) {
		Assert.notNull(s, "List<BitcoinDaily> can't be null");
		return this.iBitcoinDailyDAO.saveAll(s);
	}
	
	/*
	@Override
	public BitcoinDaily findBetweenTimeStamp(Long timeStampStart, Long timeStampEnd){
		return this.iBitcoinDailyDAO.findBetweenTimeStamp(timeStampStart, timeStampEnd);
	}
	*/
	
	//Methods directly accessible through the interface, used for scraping of bitcoin data
	
	@Override
	public List<BitcoinDaily> scrape(Long startDate, Long endDate) {
		List<BitcoinDaily> bitcoinDailyList =  this.iBitcoinDailyScraperService.scrape(startDate, endDate);
		return iBitcoinDailyDAO.saveAll(bitcoinDailyList);
	}
	
	@Override
	public boolean startAutomatedScraping() {
		return this.iBitcoinDailyScraperService.startAutomatedScraping();

	}

	@Override
	public boolean endAutomatedScraping() {
		return this.iBitcoinDailyScraperService.endAutomatedScraping();
	}
	

}
