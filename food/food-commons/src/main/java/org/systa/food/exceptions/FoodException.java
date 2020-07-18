package org.systa.food.exceptions;

public class FoodException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String message;
	private String statusCode;

	public FoodException(String message) {
		super();
		this.message = message;
	}
	
	public FoodException(String message, String statusCode) {
		super();
		this.message = message;
		this.statusCode = statusCode;
	}
	
	public String getMessage(){
		return this.message;
	}
	
	public String getStatusCode(){
		return this.statusCode;
	}
}
