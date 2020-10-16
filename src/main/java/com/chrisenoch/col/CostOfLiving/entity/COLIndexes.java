package com.chrisenoch.col.CostOfLiving.entity;


import java.util.Date;
import java.util.List;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.RepresentationModel;

public class COLIndexes {
	
	private List<COLIndex> rates;
	private Date date;
	
	
	public List<COLIndex> getRates() {
		return rates;
	}
	public void setRates(List<COLIndex> rates) {
		this.rates = rates;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public COLIndexes(List<COLIndex> rates, Date date) {
		this.rates = rates;
		this.date = date;
	}
	@Override
	public String toString() {
		return "COLIndexes [rates=" + rates + ", date=" + date + "]";
	}	


}
