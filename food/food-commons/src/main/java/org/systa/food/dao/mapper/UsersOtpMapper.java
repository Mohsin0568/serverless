package org.systa.food.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;
import org.systa.food.dto.UsersOtp;

public class UsersOtpMapper implements RowMapper<UsersOtp>{
	
public UsersOtp mapRow(ResultSet arg0, int arg1) throws SQLException {
		
		UsersOtp userOtp = new UsersOtp();
		userOtp.setId(arg0.getLong("id"));
		userOtp.setActive(arg0.getBoolean("active"));
		userOtp.setOtpValue(arg0.getString("otp_value"));
		userOtp.setMobileNumber(arg0.getString("mobile_number"));
		userOtp.setUuid(arg0.getString("uuid"));
		userOtp.setCreateDate(arg0.getTimestamp("create_Date"));
		userOtp.setUpdateDate(arg0.getTimestamp("update_Date"));
		return userOtp;
	}

}
