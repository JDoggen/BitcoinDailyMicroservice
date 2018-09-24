package com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.dto.Bitcoin6HourlyDto;
import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.model.Bitcoin6Hourly;
import com.jjalgorithms.cryptocurrency.bitcoin.sixhourly.service.IBitcoin6HourlyService;

import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin
@RestController
public class Bitcoin6HourlyController {
	
	@Autowired
	private IBitcoin6HourlyService iBitcoin6HourlyService;
	
	@GetMapping("/api/bitcoin6hourly/count")
	private long count() {
		return this.iBitcoin6HourlyService.count();
	}

	@GetMapping("/api/bitcoin6hourly/getlast6hours")
	public Bitcoin6HourlyDto getLastDay() {
		Bitcoin6HourlyDto bitcoin6HourlyDto = new Bitcoin6HourlyDto();
		Bitcoin6Hourly bitcoin6Hourly = this.iBitcoin6HourlyService.getLast6Hours();
			bitcoin6HourlyDto.setClose(bitcoin6Hourly.getClose());
			bitcoin6HourlyDto.setOpen(bitcoin6Hourly.getOpen());
			bitcoin6HourlyDto.setHigh(bitcoin6Hourly.getHigh());
			bitcoin6HourlyDto.setLow(bitcoin6Hourly.getLow());
			bitcoin6HourlyDto.setVolumeBtc(bitcoin6Hourly.getVolumeBtc());
			bitcoin6HourlyDto.setVolumeCurrency(bitcoin6Hourly.getVolumeCurrency());
			bitcoin6HourlyDto.setTimeStamp(bitcoin6Hourly.getTimeStamp());
			return bitcoin6HourlyDto;
		}
	
	@GetMapping("/api/bitcoin6hourly/scrape/{start}/{end}")
	private List<Bitcoin6HourlyDto> startScraping(@PathVariable Long start, @PathVariable Long end) {
		List<Bitcoin6Hourly> listBitcoin6Hourly =  this.iBitcoin6HourlyService.scrape(start, end);
		List<Bitcoin6HourlyDto> listBitcoin6HourlyDto = new ArrayList<>();
		Bitcoin6HourlyDto bitcoin6HourlyDto = null;
		for(Bitcoin6Hourly bitcoin6Hourly : listBitcoin6Hourly) {
			bitcoin6HourlyDto = new Bitcoin6HourlyDto();
			bitcoin6HourlyDto.setTimeStamp(bitcoin6Hourly.getTimeStamp());
			bitcoin6HourlyDto.setOpen(bitcoin6Hourly.getOpen());
			bitcoin6HourlyDto.setHigh(bitcoin6Hourly.getHigh());
			bitcoin6HourlyDto.setLow(bitcoin6Hourly.getLow());
			bitcoin6HourlyDto.setClose(bitcoin6Hourly.getClose());
			bitcoin6HourlyDto.setVolumeBtc(bitcoin6Hourly.getVolumeBtc());
			bitcoin6HourlyDto.setVolumeCurrency(bitcoin6Hourly.getVolumeCurrency());
			bitcoin6HourlyDto.setWeightedPrice(bitcoin6Hourly.getWeightedPrice());
			listBitcoin6HourlyDto.add(bitcoin6HourlyDto);
		}
		return listBitcoin6HourlyDto;
	}
	
	
	@GetMapping("/api/bitcoin6hourly/scrape/startautomatedscraping")
	private void startAutomatedScraping() {
		this.iBitcoin6HourlyService.startAutomatedScraping();
	}
	
	@GetMapping("/api/bitcoin6hourly/scrape/stopautomatedscraping")
	private void endAutomatedScraping() {
		this.iBitcoin6HourlyService.endAutomatedScraping();
	}
	
	@GetMapping("api/bitcoin6hourly/between/{timeStampStart}/{timeStampEnd}")
	private List<Bitcoin6Hourly> findByTimeStampBetween(@PathVariable Long timeStampStart, @PathVariable Long timeStampEnd){
		return this.iBitcoin6HourlyService.findByTimeStampBetween( timeStampStart, timeStampEnd);
	}
	
	@GetMapping("api/bitcoin6hourly/isscraping")
	private boolean isScraping() {
		return this.iBitcoin6HourlyService.isScraping();
	}
	
	@GetMapping("api/bitcoin6hourly/firstentry")
	private String firstEntry() {
		String firstEntry = this.iBitcoin6HourlyService.firstEntry();
		return firstEntry;
	}

}
