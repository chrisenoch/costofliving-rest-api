package com.chrisenoch.col.CostOfLiving.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface RateRepository  extends JpaRepository<COLIndex,String>, CustomisedRateRepository{
	
		@Query(value = "SELECT * FROM COLINDEX WHERE DATE LIKE %?1%", nativeQuery = true)
		List<COLIndex> findByDate(String date);
		
		/**
		 * Find the COLIndex (cost of living index) for the city requested.
		 * @param city
		 * @return the COLIndex (cost of living index) for the city requested.
		 */
		Optional<COLIndex> findByCity(String city);
		
		/**
		 * Find the COLIndexes (cost of living indexes) for all of the cities that belong to the country requested.
		 * @param country
		 * @return
		 */
		Optional<List<COLIndex>> findByCountry(String country);
		
		/**
		 * * Get the COLIndexes (cost of living indexes) for all countries that match the short country name. This will yield all countries that begin with the sequence of 
		 * letters entered as the method argument. E.g. "I" would return the rates for all cities for Italy and Iceland, "Ic" would only 
		 * take into account Iceland, and "e" would return  neither of the two. The method argument is case insensitive. 
		 * @param country
		 * @return
		 */
		Optional<List<COLIndex>> findByCountryStartingWith(String country);
		
		
}
