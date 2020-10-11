package com.chrisenoch.col.CostOfLiving.service;

import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Service;

import com.chrisenoch.col.CostOfLiving.entity.COLResults;
import com.chrisenoch.col.CostOfLiving.entity.COLIndex;


public interface CostOfLivingService {
	public List<COLIndex> findColIndexes();
	
	public List<COLIndex> findColIndexes(Date theDate);
	
	public COLResults calculateEquivalentSalary(float amount, String base, String code);
	
	public List<COLIndex> findColIndexesByCountry(String country);
	
	public List<COLResults> calculateEquivalentSalaryByCountry(float amount, COLIndex colIndex,  String country);
	
	
}
