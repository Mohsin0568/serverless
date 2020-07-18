package org.systa.food.util;

import java.util.Random;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Utils {

	public static DataSource getDataSource(){
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		//driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/food?autoReconnect=true");
		driverManagerDataSource.setUrl("jdbc:mysql://food.cuvtng7pmcoi.us-east-2.rds.amazonaws.com:3306/food?autoReconnect=true");
		driverManagerDataSource.setUsername("food");
		driverManagerDataSource.setPassword("VeryNice123$");
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return driverManagerDataSource;
	}
	
	
	public static String generateOtp(){
		String numbers = "1234567890";
		Random random = new Random();
	    char[] otp = new char[4];

	    for(int i = 0; i< 4 ; i++) {
	       otp[i] = numbers.charAt(random.nextInt(numbers.length()));
	    }
	    return new String(otp);
	}
}
