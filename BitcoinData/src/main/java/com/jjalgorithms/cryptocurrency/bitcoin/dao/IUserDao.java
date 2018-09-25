package com.jjalgorithms.cryptocurrency.bitcoin.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;

@Component
public interface IUserDao extends CrudRepository< User, Long> { 

}
