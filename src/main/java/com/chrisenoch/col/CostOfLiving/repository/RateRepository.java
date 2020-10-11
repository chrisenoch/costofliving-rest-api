package com.chrisenoch.col.CostOfLiving.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface RateRepository  extends JpaRepository<COLIndex,String>{
		List<COLIndex> findByDate(Date date);
		COLIndex findByCountry(String code);
}
