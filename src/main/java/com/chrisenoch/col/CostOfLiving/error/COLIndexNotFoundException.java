package com.chrisenoch.col.CostOfLiving.error;

public class COLIndexNotFoundException extends RuntimeException {


	public COLIndexNotFoundException(String message, Throwable cause) {
		super("The record could not be found. Method called with an argument of: '" + message + "' ", cause);

	}

	public COLIndexNotFoundException(String message) {
		super("The record could not be found. Method called with an argument of: '" + message + "' ");

	}

	public COLIndexNotFoundException(Throwable cause) {
		super(cause);

	}
	
	
}
