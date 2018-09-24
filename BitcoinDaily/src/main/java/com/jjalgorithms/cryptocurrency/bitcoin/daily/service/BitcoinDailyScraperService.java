package com.jjalgorithms.cryptocurrency.bitcoin.daily.service;

import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jjalgorithms.cryptocurrency.bitcoin.daily.dao.IBitcoinDailyDAO;
import com.jjalgorithms.cryptocurrency.bitcoin.daily.model.BitcoinDaily;

@Service
public class BitcoinDailyScraperService implements IBitcoinDailyScraperService {
	
	/* 		
	 * CONFIG
	 */
	
	final private String userAgent = "Mozilla/17.0";
	final private String address = "https://bitcoincharts.com/charts/chart.json?m=bitstampUSD&SubmitButton=Draw"
									+ "&i=Daily"
									+ "&c=1";
	final private int timeUnitInSeconds = 86_400;			//Minutely = 1, daily = 86_400 etc.
	final private int scrapingDelay = 1000;					//Delay when scraping, in seconds
	final private int scrapingMagnitude = 20;				//Howmany timestamps should be requested each scrapingDelay
	

	
	private boolean automatedScraping = false;
	
	@Autowired
	private IBitcoinDailyDAO iBitcoinDailyDAO;


	
	/*
	 * Methods directly accesible through the interface, used for scraping
	 */
	
	@Override
	public boolean isScraping() {
		return automatedScraping;
	}
	
	@Override
	public List<BitcoinDaily> scrape(Long startDate, Long endDate) {
		return this.scrapeData(startDate, endDate);
	}
	
	@Override
	public boolean startAutomatedScraping() {
		return automatedScraping = true;		
	}
	
	@Override
	public boolean endAutomatedScraping() {
		return automatedScraping = false;
	}
	
	@Override
	public BitcoinDaily getLastDay() {
		return scrapeLastDay();
	}
	

	/*
	 * Methods private to the class, used for scraping and parsing data
	 */
	
	
	@Scheduled(fixedDelay = scrapingDelay)
	private void automatedScraping() {
		if(automatedScraping) {
			BitcoinDaily firstEntry = this.iBitcoinDailyDAO.findFirstByOrderByTimeStamp();
			List<BitcoinDaily> scrapedEntries = scrape(firstEntry.getTimeStamp() - scrapingMagnitude*timeUnitInSeconds, firstEntry.getTimeStamp());
			if(scrapedEntries.size() == 0) {
				this.automatedScraping = false;
			}
			this.iBitcoinDailyDAO.saveAll(scrapedEntries);
		}
	}
	
	
	//Scrape data from solely the last day
	private BitcoinDaily scrapeLastDay() {
		Document doc = null;
		try {
			doc = connect("&r=1");
		} catch (Exception e) {
			System.out.println("Could not connect to address");
			System.out.println("Message"  + e.getMessage());
			e.printStackTrace();
		}
		StringBuilder data = getData(doc);
		BitcoinDaily lastBitcoinDaily = parse(data).get(0);
		return lastBitcoinDaily;
	}
	
	
	//Scrape data, using initial start- and endDate
	private List<BitcoinDaily> scrapeData(Long startDate, Long endDate){
		Document doc = null;
		try {																			
			 doc = connect(startDate, endDate);
		} catch(Exception e) {
			System.out.println("Could not connect to address");
			System.out.println("Message"  + e.getMessage());
			e.printStackTrace();
		}
		
		StringBuilder data = getData(doc);
		List<BitcoinDaily> bitcoinDailyList = parse(data);
		return bitcoinDailyList;
	}
	
	//Make connectiong to the website using start- and enddate
	private Document connect(Long startDate, Long endDate) throws Exception{
		String start = unixToDate(startDate);
		String end = unixToDate(endDate);
		return Jsoup.connect(address +  "&s=" + start + "&e=" + end)
				.userAgent(userAgent)
				.get();
	}
	
	//Make connectiong to the website using custom parameters
	private Document connect(String parameters) throws Exception{
		return Jsoup.connect(address + parameters)
				.userAgent(userAgent)
				.get();
	}
	
	//return <body></body> of the document as a Stringbuilder (Contains all possible data)
	private StringBuilder getData(Document doc) {
		Elements body = doc.select("body");
		return new StringBuilder(body.toString());
	}
	
	//Method that parses all data, cuts into smaller chunks and creates new BitcoinDaily objects
	private List<BitcoinDaily> parse(StringBuilder data){
		List<StringBuilder> stringBuilderList = parseToStringBuilders(data);
		ArrayList<BitcoinDaily> bitcoinDailyList = new ArrayList<BitcoinDaily>();
		BitcoinDaily bitcoinDaily = new BitcoinDaily();
		for(StringBuilder dataToParse : stringBuilderList) {
			bitcoinDaily = new BitcoinDaily();
			
			try {
				//Get and delete the timestamp
				int comma = dataToParse.indexOf(",");
				bitcoinDaily.setTimeStamp(parseToLong(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the open
				comma = dataToParse.indexOf(",");
				bitcoinDaily.setOpen(parseToDouble(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the high
				comma = dataToParse.indexOf(",");
				bitcoinDaily.setHigh(parseToDouble(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the low
				comma = dataToParse.indexOf(",");
				bitcoinDaily.setLow(parseToDouble(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the close
				comma = dataToParse.indexOf(",");
				bitcoinDaily.setClose(parseToDouble(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the volumeBTC
				comma = dataToParse.indexOf(",");
				bitcoinDaily.setVolumeBtc(parseToDouble(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the volumeCurrency
				comma = dataToParse.indexOf(",");
				bitcoinDaily.setVolumeCurrency(parseToDouble(dataToParse.substring(0, comma)));
				dataToParse.delete(0, comma + 1);
				
				//Get and delete the weightedPrice
				bitcoinDaily.setWeightedPrice(parseToDouble(dataToParse.toString()));
				
				if(fieldApproachesInfinity(bitcoinDaily)) {
					continue;
				}
				bitcoinDailyList.add(bitcoinDaily);
				
			} catch(NumberFormatException e){
				if(e.getMessage().startsWith("Error parsing  Long")) {
					System.out.println("Error parsing one of the Timestamps");
					System.out.println("Continueing");
					continue;
				} else if (e.getMessage().startsWith("Error parsing  Double")) {
					System.out.println(e.getMessage());
					System.out.print("Timestamp: ");
					System.out.println(bitcoinDaily.getTimeStamp());
					System.out.println("Continueing");
					continue;
				}
			}	
		}
		
		return bitcoinDailyList;		
	} 
	
	//Method that cuts one Stringbuilder in smaller pieces
	List<StringBuilder> parseToStringBuilders(StringBuilder data){
		ArrayList<StringBuilder> stringBuilderList = new ArrayList<StringBuilder>();
		//Delete all initial text
		int firstBracket = data.indexOf("[");
		data.delete(0, firstBracket + 1);
		
		//Find the first 
		int initBracket = data.indexOf("[");
		int endBracket = data.indexOf("]");
		while(initBracket != -1) {
			data.delete(0, initBracket + 1);																	//Delete all data leading to (and including) [
			stringBuilderList.add(new StringBuilder(data.substring(0, endBracket - (initBracket + 1))));		//Add data to the list
			
			data.delete(0, (endBracket - initBracket ));														//Delete all data, including the ]
			initBracket = data.indexOf("[");																	//Find the brackets of the next line
			endBracket = data.indexOf("]");
		}	
		return stringBuilderList;
	}
	
	Double parseToDouble(String data) throws NumberFormatException {
		Double output = null;
		try {
			output = Double.parseDouble(data.toString());
		} catch(NumberFormatException e) {
			NumberFormatException nfe = new NumberFormatException("Error parsing Long: " + data);
			nfe.setStackTrace(e.getStackTrace());
			throw nfe;
		}
		return output;
	}
	
	Long parseToLong(String data) throws NumberFormatException{
		{
			Long output = null;
			try {
				output = Long.parseLong(data.toString());
			} catch(NumberFormatException e) {
				NumberFormatException nfe = new NumberFormatException("Error parsing Double: " + data);
				nfe.setStackTrace(e.getStackTrace());
				throw nfe;
			}
			return output;
		}
	}
	
	/*
	 * Side methods, for conversion and checking
	 */
	
	private String unixToDate (Long timestamp) {
		Format formatter = new SimpleDateFormat("yyyy-MM-dd");
		String date = formatter.format(new Date((long) timestamp * 1000));
		return date;	
	}	
	
	private boolean fieldApproachesInfinity(BitcoinDaily bitcoinDaily) {
		return(
				Double.compare(bitcoinDaily.getOpen(), 1.7e308) == 0 ||
				Double.compare(bitcoinDaily.getHigh(), 1.7e308) == 0 ||
				Double.compare(bitcoinDaily.getLow(), 1.7e308) == 0 ||
				Double.compare(bitcoinDaily.getClose(), 1.7e308) == 0 ||
				Double.compare(bitcoinDaily.getVolumeBtc(), 1.7e308) == 0 ||
				Double.compare(bitcoinDaily.getVolumeCurrency(), 1.7e308) == 0 ||
				Double.compare(bitcoinDaily.getWeightedPrice(), 1.7e308) ==0);
	}
}
