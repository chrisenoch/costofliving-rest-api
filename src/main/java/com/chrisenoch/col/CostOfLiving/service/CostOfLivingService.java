package com.chrisenoch.col.CostOfLiving.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;
import com.chrisenoch.col.CostOfLiving.entity.COLResults;


 public interface CostOfLivingService {
	 List<COLIndex> findColIndexes();
	
	 List<COLIndex> findColIndexes(String theDate);
	
	 COLResults calculateEquivalentSalary(BigDecimal city1Amount, String city1, String city2);
	
	 Optional<List<COLIndex>> findColIndexesByCountry(String country);
	
		/**
		 * Calculate how much the salary for city1 would equate to for all cities that belong to the country stated. 
		 * @param city1Amount
		 * @param colIndexCity1
		 * @param country is case insensitive.
		 */
	 List<COLResults> calculateEquivalentSalaryByCountry(BigDecimal city1Amount, COLIndex colIndexCity1,  String country);
	
	 Optional<COLIndex> findByCity(String city);
	
	//To practise implementing custom methods using SpringData
	 List<COLIndex> getRatesByShortCountryName(String country);
	
	 /**
		 * * Get the COLIndexes (cost of living indexes) for all countries that match the short country name. This will yield all countries that begin with the sequence of 
		 * letters entered as the method argument. E.g. "I" would return the rates for all cities for Italy and Iceland, "Ic" would only 
		 * take into account Iceland, and "e" would return  neither of the two. The method argument is case insensitive. 
		 * @param country
		 * @return
		 */
	Optional<List<COLIndex>> findByCountryStartingWith(String country);

	
}
