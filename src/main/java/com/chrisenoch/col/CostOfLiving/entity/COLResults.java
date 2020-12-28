 package com.chrisenoch.col.CostOfLiving.entity;

import java.math.BigDecimal;

public class COLResults {
	private String city1;
	private String city2;
	private BigDecimal currentSalary;
	private BigDecimal isWorth;
	
	public String getCity1() {
		return city1;
	}
	public void setCity1(String city1) {
		this.city1 = city1;
	}
	public String getCity2() {
		return city2;
	}
	public void setCity2(String city2) {
		this.city2 = city2;
	}
	public BigDecimal getCurrentSalary() {
		return currentSalary;
	}
	public void setCurrentSalary(BigDecimal currentSalary) {
		this.currentSalary = currentSalary;
	}
	public BigDecimal getIsWorth() {
		return isWorth;
	}
	public void setIsWorth(BigDecimal isWorth) {
		this.isWorth = isWorth;
	}
	public COLResults(String city1, String city2, BigDecimal currentSalary, BigDecimal isWorth) {
		this.city1 = city1;
		this.city2 = city2;
		this.currentSalary = currentSalary;
		this.isWorth = isWorth;
	}

}
