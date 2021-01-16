package com.chrisenoch.col.CostOfLiving.repository;

import java.util.List;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public interface CustomisedRateRepository {
	public List<COLIndex> getRatesByShortCountryName(String country);
}
