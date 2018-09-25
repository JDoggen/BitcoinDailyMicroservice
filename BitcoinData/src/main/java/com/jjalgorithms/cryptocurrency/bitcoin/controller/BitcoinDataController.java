package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import com.jjalgorithms.cryptocurrency.bitcoin.service.IBitcoinDataService;
import com.jjalgorithms.cryptocurrency.bitcoin.model.*;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.*;

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
			bitcoinDto.setHigh(bitcoinData.getHigh());
			bitcoinDto.setTimeStamp(bitcoinData.getTimeStamp());
			bitcoinDto.setVolumeBtc(bitcoinData.getClose());
			bitcoinDto.setVolumeCurrency(bitcoinData.getClose());
			bitcoinDto.setWeightedPrice(bitcoinData.getWeightedPrice());
			listBitcoinDataDto.add(bitcoinDto);
		}
		return listBitcoinDataDto;
	}
	
	

}
