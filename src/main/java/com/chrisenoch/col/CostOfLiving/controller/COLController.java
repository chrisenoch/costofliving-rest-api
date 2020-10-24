package com.chrisenoch.col.CostOfLiving.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLIndexes;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.service.CostOfLivingService;

//@Controller
//@RequestMapping("/costofliving")
public class COLController {
	
	@Autowired
	CostOfLivingService costOfLivingService;
	
	@GetMapping
	public ResponseEntity<COLIndexes> getIndexes() throws Exception{
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(), new Date()),HttpStatus.OK);
	}
	
	//Not working
	@GetMapping("/country/{country}")
	public ResponseEntity<COLIndexes> getIndexesByCountry(@PathVariable("country") String country) throws Exception{
		List<COLIndex> test = costOfLivingService.findColIndexesByCountry(country);
		test.forEach(System.out::println);
		
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexesByCountry(country), new Date()),HttpStatus.OK);
	}
	
	//Appears to work
	@GetMapping("/{date}")
	public ResponseEntity<COLIndexes> getRatesByDate(@PathVariable("date") @DateTimeFormat(pattern="yyyy-MM-dd")Date date) throws Exception{
		return new ResponseEntity<COLIndexes>(new COLIndexes(costOfLivingService.findColIndexes(date), new Date()),HttpStatus.OK);
	}
	
	
	@GetMapping("/{amount}/{base}/to/{code}")
	public ResponseEntity<COLResults>calculateCostOfLiving(@PathVariable ("amount") float amount
			, @PathVariable("base")String base
			, @PathVariable("code")String code) {
		System.out.println("amount: " + amount + " code:" + code + " base: " + base);

		return new ResponseEntity<COLResults>(costOfLivingService.calculateEquivalentSalary(amount, base, code), HttpStatus.OK);

	}
	
	
	@GetMapping("/{amount}/{base}/tocountry/{country}")
	public ResponseEntity<List<COLResults>>calculateCostOfLivingByCountry(@PathVariable ("amount") float amount
			, @PathVariable("base")String base
			, @PathVariable("country")String country) {

		//create instance of COLIndex from city value
		base = base.toUpperCase();
		COLIndex colIndex = costOfLivingService.findByCity(base);
		System.out.println("colIndex value " + colIndex);
		
		return new ResponseEntity<List<COLResults>>(costOfLivingService.calculateEquivalentSalaryByCountry(amount, colIndex, country), HttpStatus.OK);

	} 
	
	
	/*
	 * 	@GetMapping("/{amount}/{base}/to/{code}")
	String calculateCol(@PathVariable ("amount") float amount, @PathVariable("base")String base
			, @PathVariable("code")String code) {
		System.out.println("amount: " + amount + " code:" + code + " base: " + base);
		
		costOfLivingService.calculateEquivalentSalary(amount, base, code);
		
		return "mainget";
	}
	 */
	


	
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
