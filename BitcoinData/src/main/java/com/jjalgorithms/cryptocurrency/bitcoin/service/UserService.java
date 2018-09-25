package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IUserDao;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;

@Service
@Transactional
public class UserService {
	
	@Autowired
	private IUserDao iUserDao;

	public Iterable<User> findAll(){
		return iUserDao.findAll();
	}
}
