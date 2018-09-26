package com.jjalgorithms.cryptocurrency.bitcoin.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;


@Entity
public class Prediction {
	
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Long id;

	private Double oneDayPrediction;

	private Double sevenDayPrediction;
	
	@ManyToMany
	private List<BitcoinData> bitcoindata;
	
	//Getters and Setters

	public List<BitcoinData> getBitcoindata() {
		return bitcoindata;
	}

	public void setBitcoindata(List<BitcoinData> bitcoindata) {
		this.bitcoindata = bitcoindata;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getOneDayPrediction() {
		return oneDayPrediction;
	}

	public void setOneDayPrediction(Double oneDayPrediction) {
		this.oneDayPrediction = oneDayPrediction;
	}

	public Double getSevenDayPrediction() {
		return sevenDayPrediction;
	}

	public void setSevenDayPrediction(Double sevenDayPrediction) {
		this.sevenDayPrediction = sevenDayPrediction;
	}

	
}
