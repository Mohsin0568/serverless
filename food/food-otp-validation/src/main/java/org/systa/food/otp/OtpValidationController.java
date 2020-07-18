package org.systa.food.otp;

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
import org.systa.food.exceptions.FoodException;
import org.systa.food.util.HttpStatusConstants;

import com.google.gson.Gson;

public class OtpValidationController {
	
	private OtpValidationService validationService;
	
	private OtpValidationService getValidationService(){
		if(this.validationService == null){
			validationService = new OtpValidationService();
			validationService.setUsersOtpDao();
			validationService.setUsersDao();
		}
		return this.validationService;
	}
	
	public void otpValidation(InputStream inputStream, OutputStream outputStream) throws IOException, ParseException{
		Gson gson = new Gson();
		Response response = new Response();
		response.addHeader("Content-Type", "application/json");
		Map<String, Object> body = new HashMap<>();
		String statusCode = "";
		Request request = null;
		
		try{
			request = getRequestObject(inputStream);
		}
		catch(Exception e){
			body.put("errorMessage", "Unable to parse input stream");
			statusCode = HttpStatusConstants.S500;
		}
		
		OtpValidationService service = getValidationService();
		try{
			String jwtToken = service.validateOtp(request.getOtp(), request.getOtpValue());
			statusCode = HttpStatusConstants.S200;
			body.put("success", "success");
			body.put("token", jwtToken);
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
	
	private Request getRequestObject(InputStream inputStream) throws IOException, ParseException{
		JSONParser parser = new JSONParser();
		BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
		JSONObject event = (JSONObject) parser.parse(reader);
		
		Gson gson = new Gson();
        return gson.fromJson(event.get("body").toString(), Request.class);
	}
	
	public static void main(String args[]){
		OtpValidationController controller = new OtpValidationController();
		OtpValidationService validationService = controller.getValidationService();
		System.out.println(validationService.validateOtp("de4e1336-f075-425f-b329-716a71ab4690",  "1435"));
	}

}
