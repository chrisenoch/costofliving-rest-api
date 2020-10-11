package com.chrisenoch.col.CostOfLiving.service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;

@Service
public class CostOfLivingServiceImpl implements CostOfLivingService{
	
	@Autowired
	RateRepository repository;
	
	@Override
	public List<COLIndex> findColIndexes(){
		return repository.findAll();
	}
	
	@Override
	public COLIndex findByCity(String city) {
		return repository.findByCity(city);
	}
	
	@Override
	public List<COLIndex> findColIndexes(Date theDate){
		System.out.println("Inside find by date");
		return repository.findByDate(theDate);
	}
	
	@Override
	public List<COLIndex> findColIndexesByCountry(String country){
		return repository.findByCountry(country);
	}
	
	@Override
	public COLResults calculateEquivalentSalary(float amount, String city1, String city2) { //Improve code. See currency eg and null. Need to test for null.
		float theBase = repository.findByCity(city1).getRate();
		float theCode= repository.findByCity(city2).getRate();
		
		float total = theBase/theCode * amount;
		System.out.println("Total: " + total);
		
		return new COLResults(city1, city2, amount, total);
	}
	
	@Override
	public List<COLResults> calculateEquivalentSalaryByCountry(float amount, COLIndex colIndex,  String country) { //Improve code. See currency eg and null. Need to test for null.
		List<COLIndex> COLIndexes = findColIndexesByCountry(country);
		//colIndex.getRate();
		//List<COLResults> results = COLIndexes.stream().mapToDouble(r->r.getRate()).
		List<COLResults> results = COLIndexes.stream().map(r -> new COLResults(colIndex.getCity(), r.getCity(), amount, colIndex.getRate()/r.getRate() * amount )
				).collect(Collectors.toList());
						
		return results;
	}
		
 
	
	/*
	 * 		//Find by country - List
		//Iterate list and create new COLResults for each object in the list 
			// ...using the value of city1 from method param and city2 from object
		//Return List of COLResults
	 */
	
	
	

}


