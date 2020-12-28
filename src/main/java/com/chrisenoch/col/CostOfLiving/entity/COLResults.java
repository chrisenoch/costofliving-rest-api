 package com.chrisenoch.col.CostOfLiving.entity;


public class COLResults {
	private String city1;
	private String city2;
	private float currentSalary;
	private float isWorth;
	
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
	public float getCurrentSalary() {
		return currentSalary;
	}
	public void setCurrentSalary(float currentSalary) {
		this.currentSalary = currentSalary;
	}
	public float getIsWorth() {
		return isWorth;
	}
	public void setIsWorth(float isWorth) {
		this.isWorth = isWorth;
	}
	public COLResults(String city1, String city2, float currentSalary, float isWorth) {
		super();
		this.city1 = city1;
		this.city2 = city2;
		this.currentSalary = currentSalary;
		this.isWorth = isWorth;
	}
	

}
