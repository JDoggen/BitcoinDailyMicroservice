package com.jjalgorithms.cryptocurrency.bitcoin.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Entity
public class Prediction {
	
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	private Double oneDayPrediction;

	private Double sevenDayPrediction;
	
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
