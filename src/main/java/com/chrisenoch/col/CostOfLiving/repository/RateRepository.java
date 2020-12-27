package com.chrisenoch.col.CostOfLiving.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface RateRepository  extends JpaRepository<COLIndex,String>, CustomisedRateRepository{
	
		@Query(value = "SELECT * FROM COLINDEX WHERE DATE LIKE %?1%", nativeQuery = true)
		List<COLIndex> findByDate(String date);
		
		Optional<COLIndex> findByCity(String city);
		Optional<List<COLIndex>> findByCountry(String country);
		
		Optional<List<COLIndex>> findByCountryStartingWith(String country);
		
		
}
