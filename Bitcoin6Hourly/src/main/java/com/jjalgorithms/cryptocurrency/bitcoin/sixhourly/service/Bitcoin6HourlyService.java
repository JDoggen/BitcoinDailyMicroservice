package com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.dao.IBitcoin6HourlyDAO;
import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.model.Bitcoin6Hourly;;

@Service
public class Bitcoin6HourlyService implements IBitcoin6HourlyService{
	
	@Autowired
	private IBitcoin6HourlyDAO iBitcoin6HourlyDAO;
	
	@Autowired
	public IBitcoin6HourlyScraperService iBitcoin6HourlyScraperService;
	
	
	//Methods directly accessible through the interface, used for interaction with the database
	
	@Override
	public long count() {
		return this.iBitcoin6HourlyDAO.count();
	}

	@Override
	public List<Bitcoin6Hourly> findAll() {
		return this.iBitcoin6HourlyDAO.findAll();
	}
	
	@Override
	public <S extends Bitcoin6Hourly> S save(S s) {
		Assert.notNull(s, "Bitcoin6Hourly can't be null");
		return this.iBitcoin6HourlyDAO.save(s);
	}

	@Override
	public <S extends Bitcoin6Hourly> List<S> saveAll(Iterable<S> s) {
		Assert.notNull(s, "List<Bitcoin6Hourly> can't be null");
		return this.iBitcoin6HourlyDAO.saveAll(s);
	}
	
	@Override
	public Bitcoin6Hourly getLast6Hours() {
		return this.iBitcoin6HourlyScraperService.getLast6Hours();
	}
	
	@Override
	public List<Bitcoin6Hourly> findByTimeStampBetween(Long timeStampStart, Long timeStampEnd){
		List<Bitcoin6Hourly> list = this.iBitcoin6HourlyDAO.findByTimeStampBetween(timeStampStart, timeStampEnd);
		return list;
	}
	
	@Override
	public String firstEntry() {
		Bitcoin6Hourly firstEntry = this.iBitcoin6HourlyDAO.findFirstByOrderByTimeStamp();
		return unixToDate(firstEntry.getTimeStamp());
	}
	
	//Methods directly accessible through the interface, used for scraping of bitcoin data
	
	@Override
	public boolean isScraping() {
		return this.iBitcoin6HourlyScraperService.isScraping();
	}
	
	@Override
	public List<Bitcoin6Hourly> scrape(Long startDate, Long endDate) {
		List<Bitcoin6Hourly> bitcoin6HourlyList =  this.iBitcoin6HourlyScraperService.scrape(startDate, endDate);
		return iBitcoin6HourlyDAO.saveAll(bitcoin6HourlyList);
	}
	
	@Override
	public boolean startAutomatedScraping() {
		return this.iBitcoin6HourlyScraperService.startAutomatedScraping();

	}

	@Override
	public boolean endAutomatedScraping() {
		return this.iBitcoin6HourlyScraperService.endAutomatedScraping();
	}

	
	//Private methods, used for parsing information
	private String unixToDate (Long timestamp) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(new Date((long) timestamp * 1000));
		return date;
	}


}
