package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.service.IBitcoinDataService;
import com.jjalgorithms.cryptocurrency.bitcoin.model.*;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.*;

@CrossOrigin
@RestController
public class BitcoinDataController {
	
	@Autowired
	private IBitcoinDataService iBitcoinDataService;
	
	@GetMapping("/api/bitcoin/count")
	private long getCount() {
		return this.iBitcoinDataService.count();
	}
	
	@GetMapping("/api/bitcoin/all")
	private List<BitcoinDataDto> findAll(){
		List<BitcoinData> listBitcoinData = this.iBitcoinDataService.findAll();
		List<BitcoinDataDto> listBitcoinDataDto = new ArrayList<>();
		for(BitcoinData bitcoinData: listBitcoinData) {
			BitcoinDataDto bitcoinDto = new BitcoinDataDto();
			bitcoinDto.setClose(bitcoinData.getClose());
			bitcoinDto.setHigh(bitcoinData.getHigh());
			bitcoinDto.setLow(bitcoinData.getLow());
			bitcoinDto.setOpen(bitcoinData.getOpen());
			bitcoinDto.setTimeStamp(bitcoinData.getTimeStamp());
			bitcoinDto.setVolumeBtc(bitcoinData.getVolumeBtc());
			bitcoinDto.setVolumeCurrency(bitcoinData.getVolumeCurrency());
			bitcoinDto.setWeightedPrice(bitcoinData.getWeightedPrice());
			listBitcoinDataDto.add(bitcoinDto);
		}
		return listBitcoinDataDto;
	}
	
	@GetMapping("/api/bitcoin/firstentry")
	private String getFirstEntry() {
		BitcoinData firstEntry = this.iBitcoinDataService.findFirstByOrderByTimeStampAsc();
		return this.iBitcoinDataService.unixToDate(firstEntry.getTimeStamp());
	}
}
