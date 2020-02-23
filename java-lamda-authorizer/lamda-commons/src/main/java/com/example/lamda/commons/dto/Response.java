package com.example.lamda.commons.dto;

import java.util.HashMap;
import java.util.Map;

public class Response {

	private boolean success;
	private Map<String, Object> data = new HashMap<>();
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
//	@Override
//	public String toString() {
//		return "Response [success=" + success + ", data=" + data + "]";
//	}		
}
