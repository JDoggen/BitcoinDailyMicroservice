package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinDaily;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.BitcoinDailyDto;
import com.jjalgorithms.cryptocurrency.bitcoin.service.IBitcoinDailyService;

@CrossOrigin
@RestController
public class BitcoinDailyController {
	
	@Autowired
	private IBitcoinDailyService iBitcoinDailyService;
	
	@GetMapping("/api/bitcoindaily/count")
	private long count() {
		return this.iBitcoinDailyService.count();
	}
	
	@GetMapping("/api/bitcoindaily/getOverallOpenAverage")				//overall average
	public Double getOveralOpenAverage() {
		return this.iBitcoinDailyService.getOverallOpenAverage();	
	}
	
	@GetMapping("/api/bitcoindaily/getOverallCloseAverage")				//overall average
	public Double getOveralCloseAverage() {
		return this.iBitcoinDailyService.getOverallCloseAverage();	
	}
	
	@GetMapping("/api/bitcoindaily/getAllOpen")
	public List<Double> getAllOpen(){
		return this.iBitcoinDailyService.getAllOpen();
	}
	
	@GetMapping("/api/bitcoindaily/getAllClose")
	public List<Double> getAllClose(){
		return this.iBitcoinDailyService.getAllClose();
	}
	
	@GetMapping("/api/bitcoindaily/getlastday")
	public BitcoinDailyDto getLastDay() {
		BitcoinDailyDto bitcoinDailyDto = new BitcoinDailyDto();
		BitcoinDaily bitcoinDaily = this.iBitcoinDailyService.getLastDay();
			bitcoinDailyDto.setClose(bitcoinDaily.getClose());
			bitcoinDailyDto.setOpen(bitcoinDaily.getOpen());
			bitcoinDailyDto.setHigh(bitcoinDaily.getHigh());
			bitcoinDailyDto.setLow(bitcoinDaily.getLow());
			bitcoinDailyDto.setVolumeBtc(bitcoinDaily.getVolumeBtc());
			bitcoinDailyDto.setVolumeCurrency(bitcoinDaily.getVolumeCurrency());
			bitcoinDailyDto.setTimeStamp(bitcoinDaily.getTimeStamp());
			return bitcoinDailyDto;
		}
	
	@GetMapping("/api/bitcoindaily/scrape/{start}/{end}")
	private List<BitcoinDailyDto> startScraping(@PathVariable Long start, @PathVariable Long end) {
		List<BitcoinDaily> listBitcoinDaily =  this.iBitcoinDailyService.scrape(start, end);
		List<BitcoinDailyDto> listBitcoinDailyDto = new ArrayList<>();
		BitcoinDailyDto bitcoinDailyDto = null;
		for(BitcoinDaily bitcoinDaily : listBitcoinDaily) {
			bitcoinDailyDto = new BitcoinDailyDto();
			bitcoinDailyDto.setTimeStamp(bitcoinDaily.getTimeStamp());
			bitcoinDailyDto.setOpen(bitcoinDaily.getOpen());
			bitcoinDailyDto.setHigh(bitcoinDaily.getHigh());
			bitcoinDailyDto.setLow(bitcoinDaily.getLow());
			bitcoinDailyDto.setClose(bitcoinDaily.getClose());
			bitcoinDailyDto.setVolumeBtc(bitcoinDaily.getVolumeBtc());
			bitcoinDailyDto.setVolumeCurrency(bitcoinDaily.getVolumeCurrency());
			bitcoinDailyDto.setWeightedPrice(bitcoinDaily.getWeightedPrice());
			listBitcoinDailyDto.add(bitcoinDailyDto);
		}
		return listBitcoinDailyDto;
	}
	
	
	@GetMapping("/api/bitcoindaily/scrape/startautomatedscraping")
	private void startAutomatedScraping() {
		this.iBitcoinDailyService.startAutomatedScraping();
	}
	
	@GetMapping("/api/bitcoindaily/scrape/endautomatedscraping")
	private void endAutomatedScraping() {
		this.iBitcoinDailyService.endAutomatedScraping();
	}
	
	@GetMapping("api/bitcoindaily/between/{timeStampStart}/{timeStampEnd}")
	private List<BitcoinDaily> findByTimeStampBetween(@PathVariable Long timeStampStart, @PathVariable Long timeStampEnd){
		return this.iBitcoinDailyService.findBytimeStampBetween( timeStampStart, timeStampEnd);
	}
	
	
	
	/*
	@GetMapping("/api/bitcoindaily/findbetweentimestamp/{start}/{end}")
	public BitcoinDaily findBetweenTimeStamp(@PathVariable Long timeStampStart, @PathVariable Long timeStampEnd){
		return this.iBitcoinDailyService.findBetweenTimeStamp(timeStampStart, timeStampEnd);
	}
	*/
}
