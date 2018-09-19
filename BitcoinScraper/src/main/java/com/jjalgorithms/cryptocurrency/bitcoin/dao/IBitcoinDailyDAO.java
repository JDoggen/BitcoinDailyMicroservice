package com.jjalgorithms.cryptocurrency.bitcoin.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinDaily;

public interface IBitcoinDailyDAO  extends CrudRepository<BitcoinDaily, Long> {
	
	@Override
	public long count();
	
	@Override
	public List<BitcoinDaily> findAll();	

	@Override
	public <S extends BitcoinDaily> S save(S s);
	
	@Override
	public <S extends BitcoinDaily> List<S> saveAll(Iterable<S> s);
	
	List<BitcoinDaily> findBytimeStampBetween(Long timeStampStart, Long timeStampEnd);
	
	//   ---> Hier ergens zit een fout!
	//@Query("SELECT b FROM BitcoinDaily b WHERE b.time_stamp = timeStampStart")
	//public BitcoinDaily findBetweenTimeStamp(@Param("timeStampStart") Long timeStampStart, @Param("timeStampEnd") Long timeStampEnd);

	//findByTimeGreaterThanAndTimeSmallerThan
	
}
