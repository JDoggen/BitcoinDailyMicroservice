package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.model.User;
import com.jjalgorithms.cryptocurrency.bitcoin.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("user")
	public Iterable<User> findAll(){
		return this.userService.findAll();
	}
}
