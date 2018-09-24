package com.jjalgorithms.cryptocurrency.bitcoin.daily.service;

import java.util.ArrayList;
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
	public List<Double> getAllOpen() {										
		List<BitcoinDaily> list =this.findAll();
		ArrayList <Double> y = new ArrayList();
		for(BitcoinDaily x: list) {
			y.add(x.getOpen());
		}
		return y;
	}
	
	@Override
	public List<Double> getAllClose() {								
		List<BitcoinDaily> list =this.findAll();
		ArrayList <Double> y = new ArrayList();
		for(BitcoinDaily x: list) {
			y.add(x.getClose());
		}
		return y;
	}
	public Double getOverallOpenAverage() {								
		List <Double> y = getAllOpen();
		Double totalOpen = 0.0;
		for(Double x: y) {
			totalOpen += x;
		}
		return totalOpen / count();
	}
	public Double getOverallCloseAverage() {							
		List <Double> y = getAllClose();
		Double totalClose = 0.0;
		for(Double x: y) {
			totalClose += x;
		}
		return totalClose / count();
	}
	
	public List<Double> getOpenBytimeStampBetween(Long timeStampStart, Long timeStampEnd) {
		List<BitcoinDaily> list = this.findBytimeStampBetween(timeStampStart, timeStampEnd);
		ArrayList <Double> y = new ArrayList();
		for(BitcoinDaily x: list) {
			y.add(x.getOpen());
		}
		return y;
	}
	
	public List<Double> getCloseBytimeStampBetween(Long timeStampStart, Long timeStampEnd) {
		List<BitcoinDaily> list = this.findBytimeStampBetween(timeStampStart, timeStampEnd);
		ArrayList <Double> y = new ArrayList();
		for(BitcoinDaily x: list) {
			y.add(x.getClose());
		}
		return y;
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
	public List<BitcoinDaily> findBytimeStampBetween(Long timeStampStart, Long timeStampEnd){
		List<BitcoinDaily> list = this.iBitcoinDailyDAO.findByTimeStampBetween(timeStampStart, timeStampEnd);
		System.out.println(list.size());
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
	public String unixToDate (Long timestamp) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(new Date((long) timestamp * 1000));
		return date;
	}

}
