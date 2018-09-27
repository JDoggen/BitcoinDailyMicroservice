package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.model.User;
import com.jjalgorithms.cryptocurrency.bitcoin.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	private UserService userService;
	
	@GetMapping("api/user")
	public Iterable<User> findAll(){
		return this.userService.findAll();
	}
	
	@GetMapping("api/user/{id}")
	public User findById(@PathVariable long id) {
		return this.userService.findById(id);
	}
	
	@PostMapping("api/user/login")
	public boolean login(@RequestBody User user) {
		return this.userService.login(user);
	}
}
