package org.systa.food.update;

import java.sql.Timestamp;

import org.systa.food.dao.UsersDao;
import org.systa.food.dao.impl.UsersDaoImpl;
import org.systa.food.dto.Users;
import org.systa.food.exceptions.FoodException;
import org.systa.food.util.HttpStatusConstants;
import org.systa.food.util.Utils;

public class UpdateService {
	
	UsersDao usersDao;
	
	public void setUsersDao(){
		usersDao = new UsersDaoImpl();
		usersDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public boolean updateUser(Users user){
		checkForMandatoryFields(user);
		user.setName(user.getFullName().split(" ")[0]);
		Users tempUser = usersDao.getUserById(user.getId());
		if(tempUser == null){
			// throw exception, since user not found
			throw new FoodException("User not found for update", HttpStatusConstants.S404);
		}
		
		if(!tempUser.getActive()){ // do not udpate the record if user is not active.
			throw new FoodException("User is not active", HttpStatusConstants.S401);
		}
		
		if(tempUser.getEmail() != null){
			user.setEmail(tempUser.getEmail()); // setting email available in database, not overwriting the email received in request.
		}
		else{
			validateEmail(user.getEmail()); // validating email
		}
		user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		return usersDao.updateUser(user);
	}
	
	public void checkForMandatoryFields(Users user){
		if(user.getFullName() == null || user.getFullName().isEmpty() || user.getId() == null || user.getId() == 0){
			throw new FoodException("All mandatory fields are required", HttpStatusConstants.S400);
		}		
	}
	
	public void validateEmail(String email){
		if(email != null && !email.isEmpty()){ // skipping email validation if email is empty, since email is not mandatory.
			String regex = "^[\\w-_\\.+]*[\\w-_\\.]\\@([\\w]+\\.)+[\\w]+[\\w]$";
			if(!email.matches(regex)){
				throw new FoodException("Invalid Email address", HttpStatusConstants.S400);
			}
		}
	}

}
