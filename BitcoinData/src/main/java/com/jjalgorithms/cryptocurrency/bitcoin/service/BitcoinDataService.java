package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.*;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;

@Service
public class BitcoinDataService implements IBitcoinDataService {

	
	@Autowired
	private IBitcoinDataDao iBitcoinDailyDAO;
	
	//Methods directly accessible through the interface, used for interaction with the database
	
	@Override
	public long count() {
		return this.iBitcoinDailyDAO.count();
	}
	
	@Override
	public List<BitcoinData> findAll(){
		return this.iBitcoinDailyDAO.findAll();
	}
	
	@Override
	public <S extends BitcoinData> S save(S s) {
		return this.iBitcoinDailyDAO.save(s);
	}
	
	@Override
	public <S extends BitcoinData> List<S> saveAll(Iterable <S> s) {
		return this.iBitcoinDailyDAO.saveAll(s);
	}
	
	@Override
	public List<BitcoinData> findByTimeStampBetweenOrderByTimeStampAsc(Long timeStampStart, Long timeStampEnd){
		return this.iBitcoinDailyDAO.findByTimeStampBetweenOrderByTimeStampAsc(timeStampStart, timeStampEnd);
	}
	
	@Override
	public BitcoinData findFirstByOrderByTimeStampAsc() {
		return this.iBitcoinDailyDAO.findFirstByOrderByTimeStampAsc();
	}
	
	@Override
	public BitcoinData findFirstByOrderByTimeStampDesc() {
		return this.iBitcoinDailyDAO.findFirstByOrderByTimeStampDesc();
	}
	
	@Override
	public List<BitcoinData> getData(Date startDate, Date endDate) {
		Long startDateUnix = DateToUnix(startDate);
		Long endDateUnix = DateToUnix(endDate);
		return this.iBitcoinDailyDAO.findByTimeStampBetweenOrderByTimeStampAsc(startDateUnix, endDateUnix);
	}
	
	//Sidemethods directly accessible through the interface
	@Override
	public String firstEntry() {
		BitcoinData firstEntry = this.iBitcoinDailyDAO.findFirstByOrderByTimeStampAsc();
		return unixToDate (firstEntry.getTimeStamp());
	}
	
	@Override
	public String unixToDate(Long timeStamp) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		return formatter.format(new Date((long) timeStamp * 1000));
	}
	
	@Override
	public Long DateToUnix(Date date) {
		return date.getTime()/1000;
	}
	
	
	
	
	
	
	
	
}
