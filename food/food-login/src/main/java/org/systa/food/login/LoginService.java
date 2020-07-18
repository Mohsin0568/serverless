package org.systa.food.login;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.dao.EmptyResultDataAccessException;
import org.systa.food.dao.UsersDao;
import org.systa.food.dao.UsersOtpDao;
import org.systa.food.dao.impl.UsersDaoImpl;
import org.systa.food.dao.impl.UsersOtpDaoImpl;
import org.systa.food.dto.Users;
import org.systa.food.dto.UsersOtp;
import org.systa.food.exceptions.FoodException;
import org.systa.food.util.HttpStatusConstants;
import org.systa.food.util.Utils;

public class LoginService {
	
	UsersDao usersDao;
	
	UsersOtpDao usersOtpDao;
	
	public void setUsersDao(){
		usersDao = new UsersDaoImpl();
		usersDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public void setUsersOtpDao(){
		usersOtpDao = new UsersOtpDaoImpl();
		usersOtpDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public Users login(String mobileNumber){
		Users user = null;
		if(!validateMobileNumber(mobileNumber)){
			// invalid mobile number exception
			throw new FoodException("Mobile Number is invalid", HttpStatusConstants.S400);
		}
		try{
			user = getUserByMobileNumber(mobileNumber);
			user.setNewUser(false);
		}
		catch(EmptyResultDataAccessException e){
			user = new Users();
			user.setMobileNumber(mobileNumber);
			user.setNewUser(true);
			user.setActive(true);
		}
		
		if(!user.getActive()){
			// user not active excception
			throw new FoodException("Users is inactive", HttpStatusConstants.S401);
		}
		
		// sending OTP
		user.setUuid(UUID.randomUUID().toString());
		if(!sendOtp(user)){
			// otp not send exception
			throw new FoodException("exception occurred while sending otp", HttpStatusConstants.S500);
		}
		return user;
	}
	
	public Users getUserByMobileNumber(String mobileNumber){
		Users user = null;
		try{
			user = usersDao.getUserByMobileNumber(mobileNumber);
		}
		catch(EmptyResultDataAccessException e){
			throw e;
		}
		return user;
	}
	
	private boolean validateMobileNumber(String mobileNumber){
		if(mobileNumber == null || mobileNumber.isEmpty())
			return false;
		Pattern p = Pattern.compile("(0/91)?[7-9][0-9]{9}");
        Matcher m = p.matcher(mobileNumber); 
        return (m.find() && m.group().equals(mobileNumber)); 
	}
	
	public boolean sendOtp(Users user){
		String otp = Utils.generateOtp();
		
		UsersOtp usersOtp = new UsersOtp();
		usersOtp.setUuid(user.getUuid());
		usersOtp.setActive(true);
		usersOtp.setMobileNumber(user.getMobileNumber());
		usersOtp.setOtpValue(otp);
		usersOtp.setCreateDate(new Timestamp(System.currentTimeMillis()));
		usersOtp.setUpdateDate(new Timestamp(System.currentTimeMillis()));
		usersOtpDao.createOtp(usersOtp);
		// send otp sm
		return true;
	}

}
