package com.example.lamda.user;

import java.util.HashMap;
import java.util.Map;

import com.example.lamda.commons.dao.UsersDao;
import com.example.lamda.commons.dao.impl.UsersDaoImpl;
import com.example.lamda.commons.dto.Response;
import com.example.lamda.commons.dto.Users;
import com.example.lamda.commons.utils.Utils;

public class UserService {
	
	UsersDao usersDao;	
	
	public void setUsersDao(){
		usersDao = new UsersDaoImpl();
		usersDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public Response getUsersById(Long id){
		Response response = new Response();
		Users users = usersDao.getUserById(id);
		if(users != null){
			Map<String, Object> user = new HashMap<>();
			user.put("data", users);
			response.setSuccess(true);
			response.setData(user);			
		}
		else{
			response.setSuccess(false);
		}
		return response;
	}
	
	

}
