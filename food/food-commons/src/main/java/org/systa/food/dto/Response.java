package org.systa.food.dto;

import java.util.HashMap;
import java.util.Map;

public class Response {

	private String statusCode;
	private String body;
	private Map<String, String> headers = new HashMap<>();
	
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}	
	public Map<String, String> getHeaders() {
		return headers;
	}
	
	public void addHeader(String arg1, String arg2){
		headers.put(arg1, arg2);
	}
		
}
