package com.chrisenoch.col.CostOfLiving.entity;


public class COLResults {
	private String city1;
	private String city2;
	private float amount;
	private float total;
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
	public float getAmount() {
		return amount;
	}
	public void setAmount(float smount) {
		this.amount = smount;
	}
	public float getTotal() {
		return total;
	}
	public void setTotal(float total) {
		this.total = total;
	}
	public COLResults(String city1, String city2, float amount, float total) {
		super();
		this.city1 = city1;
		this.city2 = city2;
		this.amount = amount;
		this.total = total;
	}
	@Override
	public String toString() {
		return "COLResults [city1=" + city1 + ", city2=" + city2 + ", amount=" + amount + ", total=" + total + "]";
	}
	
	

}
