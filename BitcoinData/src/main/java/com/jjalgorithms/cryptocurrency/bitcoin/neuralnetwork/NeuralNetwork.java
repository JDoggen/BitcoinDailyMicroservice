package com.jjalgorithms.cryptocurrency.bitcoin.neuralnetwork;

import java.util.ArrayList;
import java.util.Arrays;

public class NeuralNetwork {
	
	private final double learningRate  =  0.2;
	
	private final int NETWORK_SIZE;
	private final int NETWORK_INPUT_SIZE;
	private final int NETWORK_OUTPUT_SIZE;
	
	private int[] NETWORK_LAYER_SIZE;
	private double[][] NETWORK_NODES;
	private double[][] NETWORK_ERROR;
	private double[][] NETWORK_BIAS;
	private double[][][] NETWORK_WEIGHTS;

	
	public NeuralNetwork(int... dimensions) {
		NETWORK_LAYER_SIZE = dimensions;
		NETWORK_SIZE = dimensions.length;
		NETWORK_INPUT_SIZE = dimensions[0];
		NETWORK_OUTPUT_SIZE = dimensions[NETWORK_SIZE - 1];
		
		NETWORK_NODES = new double[NETWORK_SIZE][];
		for(int layer = 0; layer < NETWORK_SIZE; layer++) {
			NETWORK_NODES[layer] = new double[dimensions[layer]];
		}
		
		NETWORK_ERROR = new double[NETWORK_SIZE][];
		for(int layer = 0; layer < NETWORK_SIZE; layer++) {
			NETWORK_ERROR[layer] = new double[dimensions[layer]];
		}
		
		NETWORK_WEIGHTS = new double[NETWORK_SIZE][][];
		for(int layer = 0; layer < NETWORK_SIZE - 1; layer++) {
			NETWORK_WEIGHTS[layer] = new double[NETWORK_LAYER_SIZE[layer]][NETWORK_LAYER_SIZE[layer+1]];
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron ++) {
				NETWORK_WEIGHTS[layer][neuron] = randomFill(new double[NETWORK_LAYER_SIZE[layer+1]]);
			}
		}	
		
		NETWORK_BIAS = new double[NETWORK_SIZE][];
		for(int layer =0; layer < NETWORK_SIZE; layer++) {
			NETWORK_BIAS[layer] = new double[NETWORK_LAYER_SIZE[layer]];
			if(layer > 0) {
				Arrays.fill(NETWORK_BIAS[layer], 1);
			}
		}
	}
	
	public double[] forwardPropagation(double... input) {
		if(input.length != NETWORK_INPUT_SIZE) return null;
		NETWORK_NODES[0] = input;
		
		for(int layer = 1; layer < NETWORK_SIZE; layer++) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
				double sum = 0;
				for(int previousNeuron = 0; previousNeuron < NETWORK_LAYER_SIZE[layer-1]; previousNeuron++) {
					sum += NETWORK_NODES[layer-1][previousNeuron] * NETWORK_WEIGHTS[layer-1][previousNeuron][neuron];
				}
				NETWORK_NODES[layer][neuron] = sigmoid(sum + NETWORK_BIAS[layer][neuron]);
			}
		}
		return NETWORK_NODES[NETWORK_SIZE-1];
	}
	
	public void train(double[] input, double[] target) {
		forwardPropagation(input);
		calculateErrors(target);
		
		//adjust weights
		for(int layer = 0; layer < NETWORK_SIZE - 1; layer++) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
				for(int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZE[layer+1]; nextNeuron++) {
					NETWORK_WEIGHTS[layer][neuron][nextNeuron]  += -learningRate * NETWORK_ERROR[layer+1][nextNeuron] * NETWORK_NODES[layer][neuron];
				}
			}
		}
		
		//adjust bias
		for(int layer = 1; layer < NETWORK_SIZE; layer++) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
				NETWORK_BIAS[layer][neuron] += -learningRate * NETWORK_ERROR[layer][neuron]; 
			}
		}
	}
	
	
	public void calculateErrors(double[] target) {
		//Calculate error for output layer first
		for(int neuron = 0; neuron < NETWORK_OUTPUT_SIZE; neuron++) {
			NETWORK_ERROR[NETWORK_SIZE-1][neuron] = (NETWORK_NODES[NETWORK_SIZE-1][neuron] - target[neuron]) 
					* NETWORK_NODES[NETWORK_SIZE-1][neuron]
							* (1 - NETWORK_NODES[NETWORK_SIZE-1][neuron]);
		}
		
		//Calculate error for inner layers
		for(int layer = NETWORK_SIZE - 2; layer > 0; layer--) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron ++) {
				double sum = 0; 
				for(int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZE[layer+1]; nextNeuron++) {
					sum += NETWORK_WEIGHTS[layer][neuron][nextNeuron] * NETWORK_ERROR[layer + 1][nextNeuron];
				}
				NETWORK_ERROR[layer][neuron] = sum * NETWORK_NODES[layer][neuron] * (1 - NETWORK_NODES[layer][neuron]);
			}
		}
	}	
	
	public double sigmoid(double x) {
		return 1.0/(1 + Math.exp(-x));
	}
	
	public double[] randomFill(double[] array) {
		for(int i = 0; i < array.length; i++) {
			array[i] = Math.random() * 2 - 1;
		}
		return array;
	}	
	
	
	
	public double[][] getNETWORK_BIAS() {
		return NETWORK_BIAS;
	}

	public void setNETWORK_BIAS(double[][] NETWORK_BIAS) {
		this.NETWORK_BIAS = NETWORK_BIAS;
	}

	public double[][][] getNETWORK_WEIGHTS() {
		return NETWORK_WEIGHTS;
	}

	public void setNETWORK_WEIGHTS(double[][][] NETWORK_WEIGHTS) {
		this.NETWORK_WEIGHTS = NETWORK_WEIGHTS;
	}

	//method used for saving the network
	public ArrayList<String> getNetworkData(){
		ArrayList<String> networkData = new ArrayList<>();
		networkData.add(Integer.toString(NETWORK_SIZE));
		for(int layer = 0; layer < NETWORK_SIZE; layer++) {
			networkData.add(Integer.toString(NETWORK_LAYER_SIZE[layer]));
		}
		for(int layer = 0; layer < NETWORK_SIZE; layer++) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
				networkData.add(Double.toString(NETWORK_BIAS[layer][neuron]));
			}
		}
		for(int layer = 0; layer < NETWORK_SIZE - 1; layer++) {
			for(int neuron = 0; neuron < NETWORK_LAYER_SIZE[layer]; neuron++) {
				for(int nextNeuron = 0; nextNeuron < NETWORK_LAYER_SIZE[layer+1]; nextNeuron++) {
					networkData.add(Double.toString(NETWORK_WEIGHTS[layer][neuron][nextNeuron]));
				}
			}
		}
		return networkData;
	}
	
	//method used for loading network
	public static NeuralNetwork restoreNetwork(ArrayList<String> data) {
		int networkSize = Integer.parseInt(data.remove(0));
		int[] networkLayerSize = new int[networkSize];
		
		for(int layer = 0; layer < networkSize; layer++) {
			networkLayerSize[layer] = Integer.parseInt(data.remove(0));
		}
		
		NeuralNetwork network = new NeuralNetwork(networkLayerSize);
		double[][] networkBias = new double[networkSize][];
		double[][][] networkWeights = new double[networkSize][][];
		
		for(int layer = 0; layer < networkSize; layer++) {
			networkBias[layer] = new double[networkLayerSize[layer]];
			for(int neuron = 0; neuron < networkLayerSize[layer]; neuron++) {
				networkBias[layer][neuron] = Double.parseDouble(data.remove(0));
			}
		}
		
		for(int layer = 0; layer < networkSize - 1; layer++) {
			networkWeights[layer] = new double[networkLayerSize[layer]][];
			for(int neuron = 0; neuron < networkLayerSize[layer]; neuron++) {
				networkWeights[layer][neuron] = new double[networkLayerSize[layer+1]];
				for(int nextNeuron = 0; nextNeuron < networkLayerSize[layer + 1]; nextNeuron++) {
					networkWeights[layer][neuron][nextNeuron] = Double.parseDouble(data.remove(0));
				}
			}
		}
		network.setNETWORK_BIAS(networkBias);
		network.setNETWORK_WEIGHTS(networkWeights);
		return network;
	}
}
