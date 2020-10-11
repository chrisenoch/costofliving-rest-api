package com.chrisenoch.col.CostOfLiving.service;

import java.util.Date;
import java.util.List;

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
		
	
	

}
