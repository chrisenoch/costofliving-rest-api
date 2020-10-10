package com.chrisenoch.col.CostOfLiving.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;

@Controller
@RequestMapping("/costofliving")
public class COLController {
	
	@Autowired
	RateRepository repository;
	
	@GetMapping
	String showCOlIndexes() {
		//To do
		//print rates
		System.out.println("get endpoint");
		List<COLIndex> colIndexes = repository.findAll();
		System.out.println("Printing rates" + colIndexes);
		colIndexes.forEach(System.out::println);
		
		return "mainget";
		
	}
	
	@GetMapping("/{date}")
	String ratesByDate(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd")Date date) {
		//To do
		//print rates
		List<COLIndex> rates = repository.findByDate(date);
		System.out.println("Printing rates by date" + rates);
		rates.forEach(System.out::println);
		System.out.println("Date received: " + date);
		System.out.println("Gogggoo");
		return "mainget";
	}
	
	@GetMapping("/{amount}/{base}/to/{code}")
	String calculateCol(@PathVariable ("amount") float amount, @PathVariable("base")String base
			, @PathVariable("code")String code) {
		System.out.println("amount: " + amount + " code:" + code + " base: " + base);
		
		processCalculateCol(amount, base, code);
		
		return "mainget";
	}
	
	private void processCalculateCol(float amount, String base, String code) {
		float thebase = repository.findByCode(base).getRate();
		float theCode= repository.findByCode(code).getRate();
		
		float total = thebase/theCode * amount;
		System.out.println("Total: " + total);
		
		//return new CurrencyExchange
	}
	
	// get rate by base code
	//get rate by code
	
	//calculate the ratio and multiply this by the amount
	
	//below are totals, not rates
	//a = 90  and b = 130
	// I need to know how much more or less is a compared to b
	//a/b * amount 
	
//	repository.save(new Rate("EUR",0.88857F,new Date()));
//	repository.save(new Rate("JPY",102.17F,new Date()));
//	repository.save(new Rate("GBP",0.75705F,new Date()));
//	repository.save(new Rate("MXN",19.232F,new Date()));
//	repository.save(new Rate("GBP",0.75705F,new Date()));
	
	
	
	
	
	
	
	//@RequestMapping(path="/new",method = {RequestMethod.POST})
	

}
