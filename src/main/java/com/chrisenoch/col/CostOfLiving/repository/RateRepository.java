package com.chrisenoch.col.CostOfLiving.repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface RateRepository  extends JpaRepository<COLIndex,String>{
		List<COLIndex> findByDate(OffsetDateTime date);
		Optional<COLIndex> findByCity(String city);
		Optional<List<COLIndex>> findByCountry(String country);
}
