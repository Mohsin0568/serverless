package org.systa.food.controller;

import java.sql.Timestamp;

import javax.sql.DataSource;

import org.systa.food.dao.impl.UsersDaoImpl;
import org.systa.food.dto.Users;
import org.systa.food.util.Utils;

public class UserController {

	public static void main(String args[]){
		fetchUser("9032563079");
		//createUser("9032563079");
		
	}
	
	public static void createUser(String mobileNumber){
		DataSource dataSource = Utils.getDataSource();
		
		UsersDaoImpl usersDaoImpl = new UsersDaoImpl();
		usersDaoImpl.setJdbcTemplate(dataSource);
		
		Users user = new Users();
		user.setMobileNumber(mobileNumber);
		 user.setActive(true);
		 user.setCreateDate(new Timestamp(System.currentTimeMillis()));
		 user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		 System.out.println(usersDaoImpl.saveUser(user));
	}
	
	public static void fetchUser(String mobileNumber){
		DataSource dataSource = Utils.getDataSource();
		
		UsersDaoImpl usersDaoImpl = new UsersDaoImpl();
		usersDaoImpl.setJdbcTemplate(dataSource);
		
		Users user = usersDaoImpl.getUserByMobileNumber(mobileNumber);
		System.out.print(user);
	}
}
