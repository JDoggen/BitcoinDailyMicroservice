package com.jjalgorithms.cryptocurrency.bitcoin.service;


public interface INeuralNetworkService {
	
	public boolean createNetwork(int... layerSizes);
	
	public void trainRandom();
	
	public void trainAllData(int cycles);
	
	public void predict();

}
