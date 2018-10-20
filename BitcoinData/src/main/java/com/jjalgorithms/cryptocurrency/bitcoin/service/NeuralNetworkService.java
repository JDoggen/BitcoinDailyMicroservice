package com.jjalgorithms.cryptocurrency.bitcoin.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jjalgorithms.cryptocurrency.bitcoin.dao.IBitcoinDataDao;
import com.jjalgorithms.cryptocurrency.bitcoin.model.BitcoinData;
import com.jjalgorithms.cryptocurrency.bitcoin.neuralnetwork.NeuralNetwork;

@Service
public class NeuralNetworkService implements INeuralNetworkService{

	@Autowired
	IBitcoinDataDao iBitcoinDataDao;
	
	NeuralNetwork network;
	
	@Override
	public boolean createNetwork(int... layerSizes) {
		if(network != null) {
			return false;
		} else {
			File neuralNetworkData = new File("NeuralNetworkData.txt");
			if(neuralNetworkData.exists()) {
				System.out.println("Loading network data from file");
				loadNetwork();
			} else {
				System.out.println("Creating new Network");
				this.network = new NeuralNetwork(layerSizes);
			}
		}
		return true;
	}
	
	@Override
	public void trainRandom() {
		//Access random data
		Long firstTimeStamp = this.iBitcoinDataDao.findFirstByOrderByTimeStampAsc().getTimeStamp();
		Long lastTimeStamp = this.iBitcoinDataDao.findFirstByOrderByTimeStampDesc().getTimeStamp();
		
		//last time stamp should be at most be 7 days before the last timestamp
		lastTimeStamp -= 691_200;
			
		Random random = new Random();
		Long startTime = new Long( random.nextInt((int)(lastTimeStamp - firstTimeStamp)) + firstTimeStamp);
		Long endTime = startTime + 691_200;
		
		List<BitcoinData> dataSet = this.iBitcoinDataDao.findByTimeStampBetweenOrderByTimeStampAsc(startTime, endTime);
		List<BitcoinData> dataSetDays = convertToDays(dataSet);
		if(dataSetDays == null) {
			return;
		}
		train(dataSetDays);
	}
	
	@Override 
	public void trainAllData(int totalCycles){
		//Access random data
		Long firstTimeStamp = this.iBitcoinDataDao.findFirstByOrderByTimeStampAsc().getTimeStamp();
		Long lastTimeStamp = this.iBitcoinDataDao.findFirstByOrderByTimeStampDesc().getTimeStamp();
		ArrayList<List<BitcoinData>> validationList = new ArrayList<>();
		lastTimeStamp -= 691_200;
		for(int cycle = 0; cycle < totalCycles; cycle++) {
		int counter = 0;
		int setAside = 5;
			for(Long date = firstTimeStamp; date <= lastTimeStamp; date += 86_400 ) {
				counter++;
				List<BitcoinData> dataSet = this.iBitcoinDataDao.findByTimeStampBetweenOrderByTimeStampAsc(date, date + 691_200);
				List<BitcoinData> dataSetDays = convertToDays(dataSet);
				if(! (dataSetDays == null)) {
					if(counter < setAside) {
						train(dataSetDays);
					} else {
						if(cycle == 0) {
							validationList.add(dataSetDays);
						}
						counter = 0;
					}
				}
			}
			System.out.println("Trained all data, starting validation");
			validate(validationList);
			saveNetwork();
		}
	}
	
	@Override
	public void predict() {
		
	}
	
	
	//Private methods, used for parsing data
	private List<BitcoinData> convertToDays(List<BitcoinData> dataSet){	
		if(dataSet.size() < 8_000) {													//Should contain 11_520 items
			return null;
		}	
		ArrayList<BitcoinData> dataSetDays = new ArrayList<>();
		Long startTime = dataSet.get(0).getTimeStamp();
		for(int days = 1; days < 9; days ++) {
			if(days != 1) {
				startTime += 1_440;
			}
			BitcoinData day = dataSet.remove(0);
			int dataAmount = 0;
			while(!dataSet.isEmpty() && dataSet.get(0).getTimeStamp() < startTime + 1_440) {
				dataAmount++;
				BitcoinData currentData = dataSet.remove(0);
				day.setClose(currentData.getClose());
				day.setVolumeBtc(day.getVolumeBtc() + currentData.getVolumeBtc());
				day.setVolumeCurrency(day.getVolumeCurrency() + currentData.getVolumeCurrency());
				day.setWeightedPrice(day.getWeightedPrice() + currentData.getWeightedPrice());
				if(day.getHigh() < currentData.getHigh()) {
					day.setHigh(currentData.getHigh());
				}	
				if(day.getLow() > currentData.getLow()) {
					day.setLow(currentData.getLow());
				}
			}
			//normalize VolumeBTC, VolumeCurrency and weightedprice depending on amount of real 
			if(dataAmount == 0) {
				System.out.println("dataAmount == 0, not adding data to set.");
			} else {
				day.setVolumeBtc(day.getVolumeBtc() / dataAmount);
				day.setVolumeCurrency(day.getVolumeCurrency() / dataAmount);
				day.setWeightedPrice(day.getWeightedPrice() / dataAmount);
				
				dataSetDays.add(day);
			}
		}
		return dataSetDays;
	}
	
	private void train(List<BitcoinData> dataSetDays){
		double[] input = new double[42];
		double[] target = new double[2];		//Only interested in High and Close
		
		for(int day = 0; day < 7; day ++) {
			BitcoinData dayData = dataSetDays.get(day);	
			//Data is normalized
			input[0 + day * 6] = (dayData.getOpen() / 10_000.0); 
			input[1 + day * 6] = (dayData.getHigh() / 10_000.0);
			input[2 + day * 6] = (dayData.getLow() / 10_000.0);
			input[3 + day * 6] = (dayData.getClose() / 10_000.0);
			//VolumeCurrency, VolumeBTC and WeightedPrice are per minute, since these depend on amount of datapoints
			input[4 + day * 6] = (dayData.getVolumeCurrency() / 30_000);
			input[5 + day * 6] = (dayData.getVolumeBtc() / 5);
		}
		BitcoinData dayData = dataSetDays.get(7);	
		target[0] = (dayData.getHigh() / 10_000.0);
		target[1] = (dayData.getClose() / 10_000.0);
		network.train(input, target);
	}
	
	private void validate(ArrayList<List<BitcoinData>> validationList) {
		double highError = 0;
		double closeError = 0;
		int counter = 0;
		for(List<BitcoinData> dataSetDays : validationList) {
			counter++;
			double[] input = new double[42];
			for(int day = 0; day < 7; day ++) {
				BitcoinData dayData = dataSetDays.get(day);	
				//Data is normalized
				input[0 + day * 6] = (dayData.getOpen() / 10_000.0); 
				input[1 + day * 6] = (dayData.getHigh() / 10_000.0);
				input[2 + day * 6] = (dayData.getLow() / 10_000.0);
				input[3 + day * 6] = (dayData.getClose() / 10_000.0);
				input[4 + day * 6] = (dayData.getVolumeCurrency() / 50_000_000.0);
				input[5 + day * 6] = (dayData.getVolumeBtc() / 10_000.0);
			}
			double[] output = network.forwardPropagation(input);
			double predictedHigh = output[0] * 10_000;
			double predictedClose = output[1] * 10_000;
			double realHigh = dataSetDays.get(7).getHigh();
			double realClose = dataSetDays.get(7).getClose();
			highError += Math.abs((predictedHigh - realHigh) / realHigh * 100);
			closeError += Math.abs((predictedClose - realClose) / realClose * 100);
		}
		System.out.println("Average High error: " + String.format("%.2f", highError / counter) + "%");
		System.out.println("Average Close error: " + String.format("%.2f", closeError / counter) + "%");
	}
	
	private void saveNetwork(){
		File networkFile = new File("NeuralNetworkData.txt");
		if(!networkFile.exists()) {
			System.out.println("Creating new networkFile");
			try {
				networkFile.createNewFile();
			} catch(IOException e) {
				System.out.println("Error creating networkFile");
			}
		} 
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(networkFile, true));
			writer.println("=====----NEW SAVE----=====");
			ArrayList<String> networkData = network.getNetworkData();
			for(String networkDataString : networkData) {
				writer.println(networkDataString);
			}
			writer.flush();
			writer.close();
		} catch(IOException e) {
			System.out.println("Error writing to file");
		}
	}
	
	private NeuralNetwork loadNetwork() {
		File file = new File("NeuralNetworkData.txt");
		Scanner fileScanner = null;
		int position = 0;
		//First scan for the last save in the file
		try {
			fileScanner = new Scanner(file);
			int counter = 0;
			while(fileScanner.hasNextLine()) {
				counter++;
				if(fileScanner.nextLine().equals("=====----NEW SAVE----=====")){
					position = counter;
				}
			} 
		} catch (FileNotFoundException e) {
			System.out.println("error loading file");
			return null;
		} finally {
			fileScanner.close();
		}
		//Return to last save and retrieve data
		try {
			fileScanner = new Scanner(file);
			for(int counter = 0; counter < position; counter++) {
				fileScanner.nextLine();
			}
			ArrayList<String> networkData = new ArrayList<String>();
			while(fileScanner.hasNextLine()) {
				networkData.add(fileScanner.nextLine());
			}
			this.network = NeuralNetwork.restoreNetwork(networkData);
		} catch(FileNotFoundException e) {
			System.out.println("error loading file");
			return null;
		} finally {
			fileScanner.close();
		}
		return network;
	}
}
