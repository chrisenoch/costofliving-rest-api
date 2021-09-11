 package com.chrisenoch.col.CostOfLiving.entity;

import java.math.BigDecimal;

public class COLResults {
	private String city1;
	private String city2;
	
	/**
	 * The amount of money for city1
	 */
	private BigDecimal city1Amount;
	
	/**
	 * How much money city1Amount would be worth in city2
	 */
	private BigDecimal city2EquivalentWorth;
	
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
	public BigDecimal getCity1Amount() {
		return city1Amount;
	}
	public void setCity1Amount(BigDecimal city1Amount) {
		this.city1Amount = city1Amount;
	}
	public BigDecimal getCity2EquivalentWorth() {
		return city2EquivalentWorth;
	}
	public void setCity2EquivalentWorth(BigDecimal city2Equivalent) {
		this.city2EquivalentWorth = city2Equivalent;
	}
	public COLResults(String city1, String city2, BigDecimal city1Amount, BigDecimal city2Equivalent) {
		this.city1 = city1;
		this.city2 = city2;
		this.city1Amount = city1Amount;
		this.city2EquivalentWorth = city2Equivalent;
	}
	@Override
	public String toString() {
		return "COLResults [city1=" + city1 + ", city2=" + city2 + ", city1Amount=" + city1Amount + ", city2Equivalent="
				+ city2EquivalentWorth + "]";
	}
}
