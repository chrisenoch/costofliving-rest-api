package com.chrisenoch.col.CostOfLiving.entity;


import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class COLIndex {
	
	@Id
	private String city;
	private String country;
	private float rate;
	
	@JsonIgnore 
	@Temporal(TemporalType.DATE)
	Date date;

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
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

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public COLIndex(String city, String country, float rate, Date date) {
		this.city = city;
		this.country = country;
		this.rate = rate;
		this.date = date;
	}
	
	public COLIndex() {
	}

	@Override
	public String toString() {
		return "COLIndex [city=" + city + ", country=" + country + ", rate=" + rate + ", date=" + date + "]";
	}

	
	
	
}
