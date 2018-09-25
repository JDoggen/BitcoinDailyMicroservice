package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.model.Prediction;
import com.jjalgorithms.cryptocurrency.bitcoin.service.IPredictionService;


@RestController
public class PredictionController {
	
	
	@Autowired
	private IPredictionService iPredictionService;
	
	
	@GetMapping("/")												//juiste url nog toevoegen?
	public List<Prediction> findAll() {
		return this.iPredictionService.findAll();
	}
	
	/*@PostMapping("/")												//url incompleet
	public Double create(@RequestBody List<Double> closeValues) { //requestbody: JSON bericht komt binnen wordt opgeslagen in een person obj

		return this.iPersonService.create(person);

		return this.iPredictionService.create(Prediction);

		*/
	
}
