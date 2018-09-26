package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.service.IPredictionService;


@RestController
public class PredictionController {
	
	
	@Autowired
	private IPredictionService iPredictionService;
	
	@GetMapping("api/bitcoin/prediction/findall")												
	public List<Prediction> findAll() {
		return this.iPredictionService.findAll();
	}
	
	@GetMapping("api/bitcoin/prediction/findbyid/{id}")												//
	public List<Prediction> findById(@PathVariable Long id) {
		return this.iPredictionService.findAll();
	}
	
	@PostMapping("api/bitcoin/createprediction/{start}/{end}")
	public void getPrediction(@PathVariable Long start, @PathVariable Long end) {
		this.iPredictionService.getPrediction(start, end);	
	}
	
	/*@PutMapping("api/bitcoin/changeprediction/{id}/{start}/{end}")								//
	public void getPrediction(@PathVariable Long start, @PathVariable Long end) {
		this.iPredictionService.getPrediction(start, end);	
	}*/
	
	@DeleteMapping("api/bitcoin/prediction/deletebyid/{id}")
	public void deleteById(@PathVariable Long id) {
		this.iPredictionService.deleteById(id);
	}
	
}
