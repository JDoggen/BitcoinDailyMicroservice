package com.jjalgorithms.cryptocurrency.bitcoin.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.*;

@Service
public class BitcoinDataService implements IBitcoinDataService {

	
	@Autowired
	private IBitcoinDataDao iBitcoinDailyDAO;
	
	@Autowired
	public IBitcoinDailyScraperService iBitcoinDailyScraperService;
	
	//Methods directly accessible through the interface, used for interaction with the database
	
	@Override
	public long count() {
		return this.iBitcoinDailyDAO.count();
	}
	
	
	
	
}
