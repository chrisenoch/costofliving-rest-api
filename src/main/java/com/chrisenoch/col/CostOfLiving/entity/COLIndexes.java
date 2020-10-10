package com.chrisenoch.col.CostOfLiving.entity;


import java.util.Date;
import java.util.List;

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
		super();
		this.rates = rates;
		this.date = date;
	}		

}
