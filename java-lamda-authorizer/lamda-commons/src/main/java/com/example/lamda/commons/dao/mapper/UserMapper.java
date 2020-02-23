package com.example.lamda.commons.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.example.lamda.commons.dto.Users;

public class UserMapper implements RowMapper<Users>{

	public Users mapRow(ResultSet arg0, int arg1) throws SQLException {
		
		Users user = new Users();
		user.setId(arg0.getLong("id"));
		user.setActive(arg0.getBoolean("active"));
		user.setUserName(arg0.getString("userName"));
		user.setPassword(arg0.getString("password"));
		user.setMobileNumber(arg0.getString("mobileNumber"));
		user.setEmail(arg0.getString("email"));
		user.setCreateDate(arg0.getTimestamp("createDate"));
		user.setUpdateDate(arg0.getTimestamp("updateDate"));
		return user;
	}

}
