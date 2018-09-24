package com.jjalgorithms.cryptocurrency.bitcoin.sixhourly;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class BitcoinScraperApplication {

	public static void main(String[] args) {
		SpringApplication.run(BitcoinScraperApplication.class, args);
	}
}
