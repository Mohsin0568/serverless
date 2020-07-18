package org.systa.food.util;

public class QueryConstants {

	
	// f_users -- start
	
	public static final String GET_USER_BY_MOBILE_QUERY = "SELECT id, first_name firstName, mobile_number mobileNumber, "
			+ "full_name fullName, email, active, create_date createDate, update_date updateDate from f_users where mobile_number = ?";
	
	public static final String SAVE_USER = "insert into f_users (mobile_number, active, create_date, update_date) values "
			+ "(?, ?, ?, ?)";
	
	public static final String GET_USER_BY_ID = "SELECT id, first_name firstName, mobile_number mobileNumber, "
			+ "full_name fullName, email, active, create_date createDate, update_date updateDate from f_users where id = ?";
	
	public static final String UPDATE_USER_BY_ID = "update f_users set email = ?, first_name = ?, full_name = ?, update_date = ? where id = ?";
	
	// f_users -- end
	
	
	// f_users_otp -- start
	
	public static final String SAVE_USERS_OTP_DETAILS = " insert into f_users_otp (otp_value, uuid, mobile_number, active, create_date, update_date) "
			+ "values (?, ?, ?, ?, ?, ?)";
	
	
	public static final String UPDATE_USER_OTP_DETAILS = "update f_users_otp set otp_value = ?, active = ?, update_date = ? where id = ?";
	
	public static final String GET_OTP_DETAILS_BY_UUID = "select id, uuid, otp_value, mobile_number, active, create_date, update_date from f_users_otp where uuid = ?";
	
	public static final String DEACTIVE_OTP = "update f_users_otp set active = ? where id = ?";
	
	// f_users_otp -- end
}
