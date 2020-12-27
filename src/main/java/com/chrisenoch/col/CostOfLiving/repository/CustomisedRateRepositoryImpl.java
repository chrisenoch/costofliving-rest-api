package com.chrisenoch.col.CostOfLiving.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import org.springframework.beans.factory.annotation.Autowired;

import com.chrisenoch.col.CostOfLiving.entity.COLIndex;

public class CustomisedRateRepositoryImpl implements CustomisedRateRepository {
	
	EntityManager entityManager;

	@Autowired
	public CustomisedRateRepositoryImpl(EntityManager entityManager) {
		this.entityManager = entityManager;
	}

	@Override
	//To practise implementing custom methods using SpringData. 
	public List<COLIndex> getRatesByShortCountryName(String country) {
		//String queryString = "Select c from COLIndex c where c.country LIKE '%ENG%'"; //WORKS
		//String queryString = "Select c from COLIndex c where c.country = :country"; //WORKS
		//String queryString = "Select c from COLIndex c where c.country LIKE :country"; //WORKS
		String queryString = "Select c from COLIndex c where c.country LIKE :country";
		TypedQuery<COLIndex> query = entityManager.createQuery(queryString, COLIndex.class);
		query.setParameter("country", country + "%");
		
		return query.getResultList();
	}
	
}
