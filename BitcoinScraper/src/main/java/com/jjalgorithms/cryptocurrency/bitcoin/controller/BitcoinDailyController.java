package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;

import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinDaily;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.BitcoinDailyDto;
import com.jjalgorithms.cryptocurrency.bitcoin.service.IBitcoinDailyService;

@RestController
public class BitcoinDailyController {
	
	@Autowired
	private IBitcoinDailyService iBitcoinDailyService;
	
	@GetMapping("/api/bitcoindaily/count")
	private long count() {
		return this.iBitcoinDailyService.count();
	}

	@GetMapping("/api/bitcoindaily/getlastday")
		public BitcoinDailyDto getLastDay() {
			BitcoinDailyDto bitcoinDailyDto = new BitcoinDailyDto();
			bitcoinDailyDto.setClose(100d);
			bitcoinDailyDto.setOpen(150d);
			bitcoinDailyDto.setHigh(700d);
			bitcoinDailyDto.setLow(20d);
			bitcoinDailyDto.setVolumeBtc(4d);
			bitcoinDailyDto.setVolumeCurrency(500d);
			bitcoinDailyDto.setTimeStamp(10000L);
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
	
	
	
	/*
	@GetMapping("/api/bitcoindaily/findbetweentimestamp/{start}/{end}")
	public BitcoinDaily findBetweenTimeStamp(@PathVariable Long timeStampStart, @PathVariable Long timeStampEnd){
		return this.iBitcoinDailyService.findBetweenTimeStamp(timeStampStart, timeStampEnd);
	}
	*/
}
