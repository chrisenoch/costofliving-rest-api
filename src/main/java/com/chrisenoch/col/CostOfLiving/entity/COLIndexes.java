package com.chrisenoch.col.CostOfLiving.entity;


import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.hateoas.EntityModel;

public class COLIndexes {
	
	private List<EntityModel<COLIndex>> costofLivingIndexes;
	
	//@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm")
	private OffsetDateTime date;
	
	public List<EntityModel<COLIndex>> getRates() {
		return costofLivingIndexes;
	}
	public void setRates(List<EntityModel<COLIndex>> costofLivingIndexes) {
		this.costofLivingIndexes= costofLivingIndexes;
	}
 	
	public OffsetDateTime getDate() {
		return date;
	}
	public void setDate(OffsetDateTime date) {
		this.date = date;
	}

	
	public COLIndexes(List<EntityModel<COLIndex>> colIndexes, OffsetDateTime now) {
		this.costofLivingIndexes = colIndexes;
		this.date = now;
	}
	
	@Override
	public String toString() {
		return "COLIndexes [costofLivingIndexes =" + costofLivingIndexes + ", date=" + date + "]";
	}	


}
