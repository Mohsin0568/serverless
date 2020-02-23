package com.example.lamda.login;

import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.EmptyResultDataAccessException;

import com.example.lamda.commons.dao.UsersDao;
import com.example.lamda.commons.dao.impl.UsersDaoImpl;
import com.example.lamda.commons.dto.Response;
import com.example.lamda.commons.dto.Users;
import com.example.lamda.commons.utils.JwtUtil;
import com.example.lamda.commons.utils.Utils;

public class LoginService {
	
	UsersDao usersDao;	
	
	public void setUsersDao(){
		usersDao = new UsersDaoImpl();
		usersDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public Response validateLogin(String userName, String password){
		Response response = new Response();
		Users user = null;
		try{
			user = usersDao.getUserByUserName(userName);			
		}
		catch(EmptyResultDataAccessException e){
			response.setSuccess(false);
			return response;
		}
		catch(Exception e){
			System.out.print(e.getMessage());
			response.setSuccess(false);
			return response;
		}
		if(user == null){
			response.setSuccess(false);
			return response;
		}
		System.out.println("user id is " +user.getId());
		if(!(password.equals(user.getPassword()))){
			response.setSuccess(false);
			return response;
		}
		response.setSuccess(true);
		Map<String, Object> data = new HashMap<>();
		data.put("token", JwtUtil.createJwtToken(user.getId()+"", user.getMobileNumber(), ""));
		response.setData(data);
		return response;
	}	
}
