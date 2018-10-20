package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.Date;
import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;

public interface IBitcoinDataService {
	
	public long count();
	
	public List<BitcoinData> findAll();
	
	public <S extends BitcoinData> S save(S s);
	
	public <S extends BitcoinData> List<S> saveAll(Iterable<S> s);
	
	public List<BitcoinData> findByTimeStampBetweenOrderByTimeStampAsc(Long timeStampStart, Long timeStampEnd);
	
	public String unixToDate (Long timestamp);
	
	public String firstEntry();
	
	public BitcoinData findFirstByOrderByTimeStampAsc();

	public BitcoinData findFirstByOrderByTimeStampDesc();
	
	public List<BitcoinData> getData(Date startDate, Date endDate);
	
	public Long DateToUnix(Date date);
	
	public int[] missingData();
	
}
