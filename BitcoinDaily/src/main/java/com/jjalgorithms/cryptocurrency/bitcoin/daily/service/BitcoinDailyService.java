package com.jjalgorithms.cryptocurrency.bitcoin.daily.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jjalgorithms.cryptocurrency.bitcoin.daily.dao.IBitcoinDailyDAO;
import com.jjalgorithms.cryptocurrency.bitcoin.daily.model.BitcoinDaily;;

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
	
	@Override
	public BitcoinDaily getLastDay() {
		return this.iBitcoinDailyScraperService.getLastDay();
	}
	
	@Override
	public List<BitcoinDaily> findByTimeStampBetween(Long timeStampStart, Long timeStampEnd){
		List<BitcoinDaily> list = this.iBitcoinDailyDAO.findByTimeStampBetween(timeStampStart, timeStampEnd);
		return list;
	}
	
	@Override
	public String firstEntry() {
		BitcoinDaily firstEntry = this.iBitcoinDailyDAO.findFirstByOrderByTimeStamp();
		return unixToDate(firstEntry.getTimeStamp());
	}
	
	//Methods directly accessible through the interface, used for scraping of bitcoin data
	
	@Override
	public boolean isScraping() {
		return this.iBitcoinDailyScraperService.isScraping();
	}
	
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

	
	//Private methods, used for parsing information
	private String unixToDate (Long timestamp) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(new Date((long) timestamp * 1000));
		return date;
	}


}
