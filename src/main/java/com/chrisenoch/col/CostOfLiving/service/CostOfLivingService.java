package com.chrisenoch.col.CostOfLiving.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;


public interface CostOfLivingService {
	public List<COLIndex> findColIndexes();
	
	public List<COLIndex> findColIndexes(String theDate);
	
	public COLResults calculateEquivalentSalary(float amount, String base, String code);
	
	public Optional<List<COLIndex>> findColIndexesByCountry(String country);
	
	public List<COLResults> calculateEquivalentSalaryByCountry(float amount, COLIndex colIndex,  String country);
	
	public Optional<COLIndex> findByCity(String city);
	
}
