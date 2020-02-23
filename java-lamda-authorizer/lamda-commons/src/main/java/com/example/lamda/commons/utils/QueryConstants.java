package com.example.lamda.commons.utils;

public class QueryConstants {

	
	// f_users -- start
	
	public static final String GET_USER_BY_USERNAME_QUERY = "SELECT id, user_name userName, mobile_number mobileNumber, "
			+ "password, email, active, create_date createDate, update_date updateDate from f_users where user_name = ?";
	
	public static final String SAVE_USER = "insert into f_users (mobile_number, active, create_date, update_date) values "
			+ "(?, ?, ?, ?)";
	
	public static final String GET_USER_BY_ID = "SELECT id, user_name userName, password password, full_name name, mobile_number mobileNumber, "
			+ "email, active, create_date createDate, update_date updateDate from f_users where id = ?";
	
}
