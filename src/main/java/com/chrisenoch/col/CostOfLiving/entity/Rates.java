package com.chrisenoch.col.CostOfLiving.entity;


import java.util.Date;
import java.util.List;

public class Rates {
	
	private List<Rate> rates;
	private Date date;
	
	
	public List<Rate> getRates() {
		return rates;
	}
	public void setRates(List<Rate> rates) {
		this.rates = rates;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Rates(List<Rate> rates, Date date) {
		super();
		this.rates = rates;
		this.date = date;
	}		

}
