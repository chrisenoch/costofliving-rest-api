package com.chrisenoch.col.CostOfLiving.service;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.chrisenoch.col.CostOfLiving.entity.COLConverter;
import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.repository.RateRepository;

@Service
public class CostOfLivingImpl implements CostOfLiving{
	
	@Autowired
	RateRepository repository;
	
	public List<COLIndex> findColIndexes(){
		return repository.findAll();
	}
	
	public List<COLIndex> findColIndexes(Date theDate){
		return repository.findByDate(theDate);
	}
	
	public COLConverter calculateEquivalentSalary(float amount, String base, String code) { //Improve code. See currency eg and null. Need to test for null.
		float theBase = repository.findByCode(base).getRate();
		float theCode= repository.findByCode(code).getRate();
		
		float total = theBase/theCode * amount;
		System.out.println("Total: " + total);
		
		return new COLConverter(base, code, amount, total);
	}
	
	
	
	
	
	
	
	

}
