package com.chrisenoch.col.CostOfLiving.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Rate {
	
	@Id
	private String code;
	private float rate;
	
	//@JsonIgnore //Improve code
	@Temporal(TemporalType.DATE)
	Date date;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public float getRate() {
		return rate;
	}

	public void setRate(float rate) {
		this.rate = rate;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Rate(String code, float rate, Date date) {
		super();
		this.code = code;
		this.rate = rate;
		this.date = date;
	}
	
	public Rate() {
	}

	@Override
	public String toString() {
		return "Rate [code=" + code + ", rate=" + rate + ", date=" + date + "]";
	}
	
	
}
