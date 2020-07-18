package org.systa.food.dao;

import org.systa.food.dto.Users;

public interface UsersDao extends Commons {

	Users getUserByMobileNumber(String mobileNumber);
	
	long saveUser(Users user);
	
	boolean updateUser(Users user);
	
	Users getUserById(Long id);
}
