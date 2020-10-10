package com.chrisenoch.col.CostOfLiving.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chrisenoch.col.CostOfLiving.entity.Rate;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;

@Controller
@RequestMapping("/costofliving")
public class CurrencyController {
	
	@Autowired
	RateRepository repository;
	
	@GetMapping
	String showRates() {
		//To do
		//print rates
		System.out.println("get endpoint");
		List<Rate> rates = repository.findAll();
		System.out.println("Printing rates" + rates);
		rates.forEach(System.out::println);
		
		return "mainget";
		
	}
	
	@GetMapping("/{date}")
	String ratesByDate(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd")Date date) {
		//To do
		//print rates
		List<Rate> rates = repository.findByDate(date);
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
		return "mainget";
	}
	
	//@RequestMapping(path="/new",method = {RequestMethod.POST})
	

}
