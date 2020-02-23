package com.example.lamda.commons.dao;

import com.example.lamda.commons.dto.Users;

public interface UsersDao extends Commons {

	Users getUserByUserName(String userName);
	
	long saveUser(Users user);
	
	Users getUserById(Long id);
}
