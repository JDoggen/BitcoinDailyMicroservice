package com.jjalgorithms.cryptocurrency.bitcoin.service;

import com.jjalgorithms.cryptocurrency.bitcoin.model.User;

public interface IUserService {
	
	public User findById(Long id);
	
	public User findByUserName(String userName);
	
	public boolean login(User user);

}
