package com.jjalgorithms.cryptocurrency.bitcoin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
public class BitcoinDataApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinDataApplication.class, args);
	}
}
