package com.chrisenoch.col.CostOfLiving.entity;


import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.EntityModel;

public class COLIndexes {
	
	private List<EntityModel<COLIndex>> rates;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private OffsetDateTime date;
	
	public List<EntityModel<COLIndex>> getRates() {
		return rates;
	}
	public void setRates(List<EntityModel<COLIndex>> rates) {
		this.rates= rates;
	}
 	
	public OffsetDateTime getDate() {
		return date;
	}
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}

	
	public COLIndexes(List<EntityModel<COLIndex>> colIndexes, OffsetDateTime now) {
		this.rates = colIndexes;
		this.date = now;
	}
	
	@Override
	public String toString() {
		return "COLIndexes [rates=" + rates + ", date=" + date + "]";
	}	


}
