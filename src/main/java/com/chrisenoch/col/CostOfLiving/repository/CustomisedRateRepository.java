package com.chrisenoch.col.CostOfLiving.repository;

import java.util.List;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface CustomisedRateRepository {
	
	/**
	 * Get the rates for all countries that match the short country name. This will yield all countries that begin with the sequence of 
	 * letters entered as the method argument. E.g. "I" would return the rates for all cities for Italy and Iceland, "Ic" would only 
	 * take into account Iceland, and "e" would return  neither of the two. The method argument is case insensitive. 
	 * @param country
	 * @return
	 */
	public List<COLIndex> getRatesByShortCountryName(String country);
}
