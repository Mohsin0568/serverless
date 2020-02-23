package com.example.lamda.user;

import com.example.lamda.commons.dto.Response;

public class UserController {
	
	private UserService userService;
	
	private UserService getUserService(){
		if(this.userService == null){
			userService = new UserService();
			userService.setUsersDao();
		}
		return this.userService;
	}
	
	public Response getUserDetails(Request request){
		UserService userService = getUserService();
		return userService.getUsersById(Long.parseLong(request.getId()));
	}
	
	public static void main(String args[]){
		Request request = new Request();
		request.setId("6");
		UserController userController = new UserController();
		System.out.println(userController.getUserDetails(request));
	}

}
