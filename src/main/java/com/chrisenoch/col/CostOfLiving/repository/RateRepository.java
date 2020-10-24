package com.chrisenoch.col.CostOfLiving.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface RateRepository  extends JpaRepository<COLIndex,String>{
		List<COLIndex> findByDate(@Param("date") Date date);
		COLIndex findByCity(@Param("city") String city);
		List<COLIndex>findByCountry(@Param("country") String country);
		
}
