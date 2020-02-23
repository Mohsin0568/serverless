package com.example.lamda.commons.utils;

import javax.sql.DataSource;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class Utils {

	public static DataSource getDataSource(){
		DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
		//driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/food?autoReconnect=true");
		driverManagerDataSource.setUrl("jdbc:mysql://food.cuvtng7pmcoi.us-east-2.rds.amazonaws.com:3306/lamdademo?autoReconnect=true");
		driverManagerDataSource.setUsername("food");
		driverManagerDataSource.setPassword("VeryNice123$");
		driverManagerDataSource.setDriverClassName("com.mysql.jdbc.Driver");
		return driverManagerDataSource;
	}
}
