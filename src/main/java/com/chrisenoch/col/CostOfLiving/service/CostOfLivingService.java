package com.chrisenoch.col.CostOfLiving.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;


 public interface CostOfLivingService {
	 List<COLIndex> findColIndexes();
	
	 List<COLIndex> findColIndexes(String theDate);
	
	 COLResults calculateEquivalentSalary(BigDecimal amount, String base, String code);
	
	 Optional<List<COLIndex>> findColIndexesByCountry(String country);
	
	 List<COLResults> calculateEquivalentSalaryByCountry(BigDecimal amount, COLIndex colIndex,  String country);
	
	 Optional<COLIndex> findByCity(String city);
	
	//To practise implementing custom methods using SpringData
	 List<COLIndex> getRatesByShortCountryName(String country);
	
	Optional<List<COLIndex>> findByCountryStartingWith(String country);

	
}
