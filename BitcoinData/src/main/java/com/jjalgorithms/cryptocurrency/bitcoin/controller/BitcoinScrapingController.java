package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.dto.BitcoinDataDto;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.service.IBitcoinScrapingService;

@RestController
public class BitcoinScrapingController {


	@Autowired
	private IBitcoinScrapingService iBitcoinScrapingService;
	
	@GetMapping("/api/bitcoin/scrape/{start}/{end}")
	private List<BitcoinDataDto> startScraping(@PathVariable Long start, @PathVariable Long end) {
		List<BitcoinData> listBitcoinData =  this.iBitcoinScrapingService.scrape(start, end);
		List<BitcoinDataDto> listBitcoinDataDto = new ArrayList<>();
		BitcoinDataDto bitcoinDataDto = null;
		for(BitcoinData bitcoinDaily : listBitcoinData) {
			bitcoinDataDto = new BitcoinDataDto();
			bitcoinDataDto.setTimeStamp(bitcoinDaily.getTimeStamp());
			bitcoinDataDto.setOpen(bitcoinDaily.getOpen());
			bitcoinDataDto.setHigh(bitcoinDaily.getHigh());
			bitcoinDataDto.setLow(bitcoinDaily.getLow());
			bitcoinDataDto.setClose(bitcoinDaily.getClose());
			bitcoinDataDto.setVolumeBtc(bitcoinDaily.getVolumeBtc());
			bitcoinDataDto.setVolumeCurrency(bitcoinDaily.getVolumeCurrency());
			bitcoinDataDto.setWeightedPrice(bitcoinDaily.getWeightedPrice());
			listBitcoinDataDto.add(bitcoinDataDto);
		}
		return listBitcoinDataDto;
	}
}
