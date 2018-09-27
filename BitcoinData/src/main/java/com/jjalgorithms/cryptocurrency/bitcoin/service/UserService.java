package com.jjalgorithms.cryptocurrency.bitcoin.service;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IUserDao;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;

@Service
@Transactional
public class UserService implements IUserService {
	
	@Autowired
	private IUserDao iUserDao;
	

	public Iterable<User> findAll(){
		return iUserDao.findAll();
	}
	
	public User findById(Long id) {
		Optional<User> user = this.iUserDao.findById(id);
		Assert.notNull(user, "Did not find a user");
		return user.get();
	}
	
	public boolean login(User user) {
		Optional<User> originalUserOptional = this.iUserDao.findByUserName(user.getUserName());
		System.out.println(user.getUserName() + " " + user.getPassWord());
		if(!originalUserOptional.isPresent()) {
			return false;
		}
		User originalUser = originalUserOptional.get();
		System.out.println(originalUser.getUserName() + originalUser.getPassWord());
		return ((user.getUserName().equals(originalUser.getUserName()) ) && (user.getPassWord().equals(originalUser.getPassWord())));
	}

	@Override
	public User findByUserName(String userName) {
		Optional<User> userOptional = this.iUserDao.findByUserName(userName);
		Assert.notNull(userOptional, "User can't be null");
		return userOptional.get();
	}
	
	
}
