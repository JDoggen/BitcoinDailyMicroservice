package com.jjalgorithms.cryptocurrency.bitcoin.controller;

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
	
	@GetMapping("api/bitcoin/prediction/findbyid/{id}")
	public Optional<Prediction> findById(@PathVariable Long id) {
		return this.iPredictionService.findById(id);
	}
	
	@PostMapping("api/bitcoin/createprediction/{start}/{end}")
	public Prediction getPrediction(@PathVariable Long start, @PathVariable Long end) {
		return this.iPredictionService.createPrediction(start, end);
	}
	
	@PutMapping("api/bitcoin/changeprediction/{id}/{start}/{end}")
	public Prediction changePrediction(@PathVariable Long id, Long start, @PathVariable Long end) {
		return this.iPredictionService.changePrediction(id, start, end);
	}
	
	@DeleteMapping("api/bitcoin/prediction/deletebyid/{id}")
	public String deleteById(@PathVariable Long id) {
		this.iPredictionService.deleteById(id);
		return "Prediction " + id + " deleted";
	}
	
}
