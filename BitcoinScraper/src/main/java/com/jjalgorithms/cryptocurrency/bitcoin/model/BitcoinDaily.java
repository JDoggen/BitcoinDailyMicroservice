package com.jjalgorithms.cryptocurrency.bitcoin.model;

import javax.persistence.*;

@Entity
public class BitcoinDaily {
	
	@Id
	private Long timeStamp;
	
	private Double open;
	private Double close;
	private Double high;
	private Double low;
	private Double volumeBtc;
	private Double volumeCurrency;
	private Double weightedPrice;
	
	
	public Long getTimeStamp() {
		return timeStamp;
	}
	
	public void setTimeStamp(Long timeStamp) {
		this.timeStamp = timeStamp;
	}
	
	public Double getOpen() {
		return open;
	}
	
	public void setOpen(Double open) {
		this.open = open;
	}
	
	public Double getClose() {
		return close;
	}
	
	public void setClose(Double close) {
		this.close = close;
	}
	
	public Double getHigh() {
		return high;
	}
	
	public void setHigh(Double high) {
		this.high = high;
	}
	
	public Double getLow() {
		return low;
	}
	public void setLow(Double low) {
		this.low = low;
	}
	
	public Double getVolumeBtc() {
		return volumeBtc;
	}
	
	public void setVolumeBtc(Double volumeBtc) {
		this.volumeBtc = volumeBtc;
	}
	
	public Double getVolumeCurrency() {
		return volumeCurrency;
	}
	
	public void setVolumeCurrency(Double volumeCurrency) {
		this.volumeCurrency = volumeCurrency;
	}
	
	public Double getWeightedPrice() {
		return weightedPrice;
	}
	
	public void setWeightedPrice(Double weightedPrice) {
		this.weightedPrice = weightedPrice;
	}
}
