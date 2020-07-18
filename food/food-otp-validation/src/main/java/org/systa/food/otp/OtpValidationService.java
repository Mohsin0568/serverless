package org.systa.food.otp;

import java.sql.Timestamp;

import org.springframework.dao.EmptyResultDataAccessException;
import org.systa.food.dao.UsersDao;
import org.systa.food.dao.UsersOtpDao;
import org.systa.food.dao.impl.UsersDaoImpl;
import org.systa.food.dao.impl.UsersOtpDaoImpl;
import org.systa.food.dto.Users;
import org.systa.food.dto.UsersOtp;
import org.systa.food.exceptions.FoodException;
import org.systa.food.util.HttpStatusConstants;
import org.systa.food.util.JwtUtil;
import org.systa.food.util.Utils;

public class OtpValidationService {
	
	UsersOtpDao usersOtpDao;
	
	UsersDao usersDao;
	
	public void setUsersOtpDao(){
		usersOtpDao = new UsersOtpDaoImpl();
		usersOtpDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public void setUsersDao(){
		usersDao = new UsersDaoImpl();
		usersDao.setJdbcTemplate(Utils.getDataSource());
	}
	
	public String validateOtp(String uuid, String otp){
		UsersOtp usersOtp = usersOtpDao.getOtpDetailsByUUID(uuid);
		if(usersOtp.isActive() && usersOtp.getOtpValue().equals(otp)){
			usersOtpDao.deactivateOtp(usersOtp.getId());
			Users user = getUserDetails(usersOtp.getMobileNumber());
			return JwtUtil.createJwtToken(user.getId().toString(), user.getMobileNumber(), usersOtp.getUuid());
		}
		else{
			throw new FoodException("Invalid OTP", HttpStatusConstants.S401);
		}
	}
	
	public Users getUserDetails(String mobileNumber){
		Users user = getUserDetailsByMobileNumber(mobileNumber);
		if(user == null){
			user = new Users();
			user.setMobileNumber(mobileNumber);
			user.setActive(true);
			user.setCreateDate(new Timestamp(System.currentTimeMillis()));
			user.setUpdateDate(new Timestamp(System.currentTimeMillis()));
			long id = usersDao.saveUser(user);
			user.setId(id);
		}
		return user;
	}
	
	public Users getUserDetailsByMobileNumber(String mobileNumber){
		try{
			return usersDao.getUserByMobileNumber(mobileNumber);
		}
		catch(EmptyResultDataAccessException e){
			return null;
		}		
	}

}
