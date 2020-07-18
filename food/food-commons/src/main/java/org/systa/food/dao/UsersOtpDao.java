package org.systa.food.dao;

import org.systa.food.dto.UsersOtp;

public interface UsersOtpDao extends Commons {

	long createOtp(UsersOtp usersOtp);
	
	void updateOtpDetails(UsersOtp usersOtp);
	
	UsersOtp getOtpDetailsByUUID(String uuid);
	
	void deactivateOtp(Long id);
}
