package com.example.lamda.login;

import com.amazonaws.services.lambda.runtime.Context;
import com.example.lamda.commons.dto.Response;

public class LoginController {
	
	private LoginService loginService;
	
	
	private LoginService getLoginService(){
		if(this.loginService == null){
			loginService = new LoginService();
			loginService.setUsersDao();
		}
		return this.loginService;
	}
	
	public Response login(Request request,
			Context context){
		LoginService loginService = getLoginService();
		System.out.println("username is " + request.getUserName());
		System.out.println("password is " + request.getPassword());
		try{
			return loginService.validateLogin(request.getUserName(), request.getPassword());
		}
		catch(Exception e){
			e.printStackTrace();
			throw e;
		}
		
	}
	
	public static void main(String args[]){
		LoginController loginController = new LoginController();
		Request request = new Request();
		request.setUserName("lamdaDemo");
		request.setPassword("lamdaDemo");
		System.out.println(loginController.login(request, null));
	}	
}