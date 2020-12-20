package com.chrisenoch.col.CostOfLiving.error;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class COLIndexExceptionHandler {

	//add exception handler
	@ExceptionHandler
	public ResponseEntity<COLIndexErrorResponse> handleException(COLIndexNotFoundException exc){
		//create a COLIndexErrorResponse
		
		COLIndexErrorResponse error = new COLIndexErrorResponse();
		
		error.setStatus(HttpStatus.NOT_FOUND.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		//return ResponseEntity
		  
		return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);

	}
	
	//add another exception handler...do catch all for any type of exception
	@ExceptionHandler
	public ResponseEntity<COLIndexErrorResponse> handleException(Exception exc){
		COLIndexErrorResponse error = new COLIndexErrorResponse();
		
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		
		//return ResponseEntity
		
		return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);		
	}


}
	
	

