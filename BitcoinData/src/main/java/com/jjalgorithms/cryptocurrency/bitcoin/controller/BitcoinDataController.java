package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
	
	@GetMapping("/api/bitcoin/lastentry")
	private String getLastEntry() {
		BitcoinData lastEntry = this.iBitcoinDataService.findFirstByOrderByTimeStampDesc();
		return this.iBitcoinDataService.unixToDate(lastEntry.getTimeStamp());
	}
	
	@PostMapping("/api/bitcoin/getdata")
	private List<BitcoinDataDto> getData(@RequestBody DateDto dateDto) {
		List<BitcoinData> bitcoinDataList = this.iBitcoinDataService.getData(dateDto.getStartDate(), dateDto.getEndDate());
		return convertToDto(bitcoinDataList);
	}
	
	
	
	//Private methods
	private BitcoinDataDto convertToDto(BitcoinData bitcoinData) {
		BitcoinDataDto bitcoinDataDto = new BitcoinDataDto();
		bitcoinDataDto.setClose(bitcoinData.getClose());
		bitcoinDataDto.setHigh(bitcoinData.getHigh());
		bitcoinDataDto.setLow(bitcoinData.getLow());
		bitcoinDataDto.setOpen(bitcoinData.getOpen());
		bitcoinDataDto.setTimeStamp(bitcoinData.getTimeStamp());
		bitcoinDataDto.setVolumeBtc(bitcoinData.getVolumeBtc());
		bitcoinDataDto.setVolumeCurrency(bitcoinData.getVolumeCurrency());
		bitcoinDataDto.setWeightedPrice(bitcoinData.getWeightedPrice());
		return bitcoinDataDto;
	}
	
	private ArrayList<BitcoinDataDto> convertToDto(Iterable<BitcoinData> bitcoinDataList) {
		ArrayList<BitcoinDataDto> bitcoinDataDtoList = new ArrayList<>();
		for(BitcoinData bitcoinData : bitcoinDataList) {
			BitcoinDataDto bitcoinDataDto = convertToDto(bitcoinData);
			bitcoinDataDtoList.add(bitcoinDataDto);
		}
		return bitcoinDataDtoList;
	}
}
