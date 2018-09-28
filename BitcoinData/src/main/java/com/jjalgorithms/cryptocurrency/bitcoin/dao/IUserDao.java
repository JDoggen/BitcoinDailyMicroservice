package com.jjalgorithms.cryptocurrency.bitcoin.dao;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;

@Component
public interface IUserDao extends CrudRepository<User, Long> { 
	
	@Override
	public long count();
	
	@Override
	public Iterable<User> findAll();

	@Override
	public Optional<User> findById(Long id);
	
	public Optional<User> findByUserName(String userName);
	

}
