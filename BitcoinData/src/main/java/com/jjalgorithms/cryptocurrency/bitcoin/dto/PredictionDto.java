package com.jjalgorithms.cryptocurrency.bitcoin.dto;

public class PredictionDto {

	private Long id;
	
	private Double standardDeviation;
	
	private Double theFactor;

	private Double oneDayPrediction;
	
	private Double lastCloseValue;
	
	private Double averageCloseValue;
	
	private Long start;
	
	private Long end;

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Double getStandardDeviation() {
		return standardDeviation;
	}

	public void setStandardDeviation(Double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}

	public Double getTheFactor() {
		return theFactor;
	}

	public void setTheFactor(Double theFactor) {
		this.theFactor = theFactor;
	}

	public Double getOneDayPrediction() {
		return oneDayPrediction;
	}

	public void setOneDayPrediction(Double oneDayPrediction) {
		this.oneDayPrediction = oneDayPrediction;
	}

	public Double getLastCloseValue() {
		return lastCloseValue;
	}

	public void setLastCloseValue(Double lastCloseValue) {
		this.lastCloseValue = lastCloseValue;
	}

	public Double getAverageCloseValue() {
		return averageCloseValue;
	}

	public void setAverageCloseValue(Double averageCloseValue) {
		this.averageCloseValue = averageCloseValue;
	}

	public Long getStart() {
		return start;
	}

	public void setStart(Long start) {
		this.start = start;
	}

	public Long getEnd() {
		return end;
	}

	public void setEnd(Long end) {
		this.end = end;
	}
	
}
