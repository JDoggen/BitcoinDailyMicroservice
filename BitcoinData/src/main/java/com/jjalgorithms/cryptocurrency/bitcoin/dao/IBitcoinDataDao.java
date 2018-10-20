package com.jjalgorithms.cryptocurrency.bitcoin.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;

public interface IBitcoinDataDao extends CrudRepository<BitcoinData, Long>{
	
	@Override
	public long count();
	
	@Override
	public List<BitcoinData> findAll();	

	@Override
	public <S extends BitcoinData> S save(S s);
	
	@Override
	public <S extends BitcoinData> List<S> saveAll(Iterable<S> s);
	
	List<BitcoinData> findByTimeStampBetweenOrderByTimeStampAsc(Long timeStampStart, Long timeStampEnd);
	
	List<BitcoinData> findAllByOrderByTimeStampAsc();
	
	BitcoinData findFirstByOrderByTimeStampAsc();
	
	BitcoinData findFirstByOrderByTimeStampDesc();
	
}
