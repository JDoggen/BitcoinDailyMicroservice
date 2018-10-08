package com.jjalgorithms.cryptocurrency.bitcoin.dto;

import java.util.List;

import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;

public class UserDto {
	
	private Long id;
	
	private String userName;
	private String passWord;
	private List<Prediction> predictions;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassWord() {
		return passWord;
	}
	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}
	public List<Prediction> getPredictions() {
		return predictions;
	}
	public void setPredictions(List<Prediction> prediction) {
		this.predictions = prediction;
	}


}
