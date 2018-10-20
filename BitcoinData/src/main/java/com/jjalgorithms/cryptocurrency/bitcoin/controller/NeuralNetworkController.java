package com.jjalgorithms.cryptocurrency.bitcoin.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jjalgorithms.cryptocurrency.bitcoin.service.INeuralNetworkService;

@RestController
public class NeuralNetworkController {
	
	@Autowired
	INeuralNetworkService iNeuralNetworkService;
	
	@GetMapping("/api/neuralnetwork/create")
	private boolean createNetwork() {
		return this.iNeuralNetworkService.createNetwork(42, 60, 50, 25, 2);
	}
	
	@GetMapping("/api/neuralnetwork/trainrandom")
	private void trainRandom() {
		for(int i = 0; i<1_000; i++) {
			System.out.println(i);
			this.iNeuralNetworkService.trainRandom();
		}
	}
	
	@GetMapping("/api/neuralnetwork/trainall")
	private void trianAllData() {
		this.iNeuralNetworkService.trainAllData(5);
	}

}
