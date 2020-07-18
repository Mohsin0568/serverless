package org.systa.food.update;

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

import com.google.gson.Gson;

public class UpdateController {
	
	private UpdateService updateService;
	
	private UpdateService getUpdateService(){
		if(this.updateService == null){
			updateService = new UpdateService();
			updateService.setUsersDao();
		}
		return updateService;
	}
	
	public void updateUserDetails(InputStream inputStream, OutputStream outputStream) throws IOException{
		UpdateService updateService = getUpdateService();		
		Gson gson = new Gson();
		Response response = new Response();
		response.addHeader("Content-Type", "application/json");
		Map<String, Object> body = new HashMap<>();
		String statusCode = "";
		Users user = null;
		
		try{
			user = getRequestObject(inputStream);
		}
		catch(Exception e){
			body.put("errorMessage", "Unable to parse input stream");
			statusCode = HttpStatusConstants.S500;
		}		
		
		try{
			updateService.updateUser(user);
			statusCode = HttpStatusConstants.S200;
		}		
		catch(FoodException e){
			body.put("errorMessage", e.getMessage());
			statusCode = e.getStatusCode();
		}		
		catch(Exception e){
			body.put("errorMessage", e.getMessage());
			statusCode = HttpStatusConstants.S500;
		}
		
		response.setBody(new JSONObject(body).toString());
		response.setStatusCode(statusCode);
		
		OutputStreamWriter writer = new OutputStreamWriter(outputStream, "UTF-8");
	    writer.write(gson.toJson(response));
	    writer.close();
	}
	
	private Users getRequestObject(InputStream inputStream) throws IOException, ParseException{
		JSONParser parser = new JSONParser();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject event = (JSONObject) parser.parse(reader);
		
		Gson gson = new Gson();
        return gson.fromJson(event.get("body").toString(), Users.class);
	}
}
