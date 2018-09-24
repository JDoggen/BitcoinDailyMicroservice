package com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.model.Bitcoin6Hourly;

public interface IBitcoin6HourlyDAO  extends CrudRepository<Bitcoin6Hourly, Long> {
	
	@Override
	public long count();
	
	@Override
	public List<Bitcoin6Hourly> findAll();	

	@Override
	public <S extends Bitcoin6Hourly> S save(S s);
	
	@Override
	public <S extends Bitcoin6Hourly> List<S> saveAll(Iterable<S> s);
	
	List<Bitcoin6Hourly> findByTimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	Bitcoin6Hourly findFirstByOrderByTimeStamp();
	
}
