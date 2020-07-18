package org.systa.food.login;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.systa.food.dto.Response;
import org.systa.food.dto.Users;
import org.systa.food.exceptions.FoodException;
import org.systa.food.util.HttpStatusConstants;

import com.amazonaws.services.lambda.runtime.Context;
import com.google.gson.Gson;

public class LoginController {
	
	private LoginService loginService;
	
	
	private LoginService getLoginService(){
		if(this.loginService == null){
			loginService = new LoginService();
			loginService.setUsersDao();
			loginService.setUsersOtpDao();
		}
		return this.loginService;
	}
	
	public void getUsersByMobileNumber(InputStream inputStream, OutputStream outputStream,
			Context context) throws IOException, ParseException{
		Gson gson = new Gson();
		Response response = new Response();
		response.addHeader("Content-Type", "application/json");
		Map<String, Object> body = new HashMap<>();
		String statusCode = "";
		Request request = null;
		try{
			request = getRequestObject(inputStream);
			request.setMobileNumber(request.getMobileNumber());
		}
		catch(Exception e){
			body.put("errorMessage", "Unable to parse input stream");
			statusCode = HttpStatusConstants.S500;
		}	
		
		LoginService loginService = getLoginService();
		Users user = null;
		try{
			user = loginService.login(request.getMobileNumber());			
			//body.put("user", getUserJson(user));
			body.put("newUser", user.isNewUser());		
			body.put("otp", user.getUuid());
			statusCode = HttpStatusConstants.S200;
		}
		catch(FoodException e){
			body.put("errorMessage", e.getMessage());
			statusCode = e.getStatusCode();
		}
		
		catch(Exception e){
			body.put("errorMessage", e.getMessage());
			statusCode = HttpStatusConstants.S500;
			e.printStackTrace();
		}
		
		response.setBody(new JSONObject(body).toString());
		response.setStatusCode(statusCode);
		
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
	    writer.write(gson.toJson(response));
	    writer.close();
	}
	
	public static void main(String args[]){
		LoginController controller = new LoginController();
		LoginService loginService = controller.getLoginService();
		loginService.login("9032563049");
	}
	
	@SuppressWarnings("unchecked")
	public JSONObject getUserJson(Users user){
		JSONObject userJson = new JSONObject();
		userJson.put("id", user.getId());
		userJson.put("name", user.getName());
		userJson.put("fullName", user.getFullName());
		userJson.put("mobileNumber", user.getMobileNumber());
		userJson.put("email", user.getEmail());
		userJson.put("createDate", user.getCreateDate() != null ? user.getCreateDate().toString() : "");
		userJson.put("updateDate", user.getUpdateDate() != null ? user.getUpdateDate().toString() : "");
		return userJson;
	}
	
	private Request getRequestObject(InputStream inputStream) throws IOException, ParseException{
		JSONParser parser = new JSONParser();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject event = (JSONObject) parser.parse(reader);
		
		Gson gson = new Gson();
        return gson.fromJson(event.get("body").toString(), Request.class);
	}
}