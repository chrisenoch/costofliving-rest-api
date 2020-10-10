package com.chrisenoch.col.CostOfLiving.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisenoch.col.CostOfLiving.entity.Rate;

public interface RateRepository  extends JpaRepository<Rate,String>{
		List<Rate> findByDate(Date date);
		Rate findByCode(String code);
		Rate findByDateAndCode(Date date,String code);
}
