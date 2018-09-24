package com.jjalgorithms.cryptocurrency.bitcoin.daily.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jjalgorithms.cryptocurrency.bitcoin.daily.model.BitcoinDaily;

public interface IBitcoinDailyDAO  extends CrudRepository<BitcoinDaily, Long> {
	
	@Override
	public long count();
	
	@Override
	public List<BitcoinDaily> findAll();	

	@Override
	public <S extends BitcoinDaily> S save(S s);
	
	@Override
	public <S extends BitcoinDaily> List<S> saveAll(Iterable<S> s);
	
	List<BitcoinDaily> findByTimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	
	BitcoinDaily findFirstByOrderByTimeStamp();
	
}
