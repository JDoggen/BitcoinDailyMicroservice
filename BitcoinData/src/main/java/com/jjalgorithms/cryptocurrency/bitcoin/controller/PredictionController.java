package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.dto.BitcoinDataDto;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.PredictionDto;
import com.jjalgorithms.cryptocurrency.bitcoin.dto.UserDto;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.model.User;
import com.jjalgorithms.cryptocurrency.bitcoin.service.IPredictionService;


@RestController
public class PredictionController {
	
	
	@Autowired
	private IPredictionService iPredictionService;
	
	@GetMapping("api/bitcoin/prediction/findall")
	public List<PredictionDto> findAll() {
		List<Prediction> predictionList = this.iPredictionService.findAll();
		List<PredictionDto> predictionDtoList = new ArrayList<>();
		for(Prediction prediction : predictionList) {
			predictionDtoList.add(convertToDto(prediction));
		}
		return predictionDtoList;
	}
	
	@GetMapping("api/bitcoin/prediction/findbyid/{id}")          // nog fixen
	public PredictionDto findById(@PathVariable Long id) {
		Prediction prediction = this.iPredictionService.findById(id);
		return convertToDto(prediction);
	}
	
	@PostMapping("api/bitcoin/createprediction/")
	public UserDto getPrediction(@RequestBody UserDto userDto) {
		User user = new User();
		user.setUserName(userDto.getUserName());
		user.setPrediction(userDto.getPredictions());
		User newUser = this.iPredictionService.createPrediction(user);
		UserDto newUserDto = new UserDto();
		newUserDto.setUserName(newUser.getUserName());
		newUserDto.setId(newUser.getId());
		newUserDto.setPredictions(newUser.getPrediction());
		return newUserDto;
		
	}
	
	@PostMapping("/api/bitcoin/prediction")
	public List<PredictionDto> getUserPrediction(@RequestBody UserDto userDto) {
		List<Prediction> predictionList = this.iPredictionService.getUserPrediction(userDto);
		List<PredictionDto> predictionDtoList = new ArrayList<>();
		for(Prediction prediction : predictionList) {
			predictionDtoList.add(convertToDto(prediction));
		}
		return predictionDtoList;
	}
	
	
	@PutMapping("api/bitcoin/changeprediction")			// nog fixen??
	public PredictionDto changePrediction(@RequestBody PredictionDto predictionDto) {
		Prediction prediction = this.iPredictionService.changePrediction(predictionDto.getId(), predictionDto.getStart(), predictionDto.getEnd());
		return convertToDto(prediction);
	}
	
	@DeleteMapping("api/bitcoin/prediction/deletebyid/{id}")
	public String deleteById(@PathVariable Long id) {
		this.iPredictionService.deleteById(id);
		return "Prediction " + id + " deleted";
	}
	
	//Private methods
		private PredictionDto convertToDto(Prediction prediction) {
			PredictionDto predictionDto = new PredictionDto();
			predictionDto.setId(prediction.getId());
			predictionDto.setStandardDeviation(prediction.getStandardDeviation());
			predictionDto.setTheFactor(prediction.getTheFactor());
			predictionDto.setOneDayPrediction(prediction.getOneDayPrediction());
			predictionDto.setLastCloseValue(prediction.getLastCloseValue());
			predictionDto.setAverageCloseValue(prediction.getAverageCloseValue());
			predictionDto.setStart(prediction.getStart());
			predictionDto.setEnd(prediction.getEnd());
			return predictionDto;
		}
	
}
