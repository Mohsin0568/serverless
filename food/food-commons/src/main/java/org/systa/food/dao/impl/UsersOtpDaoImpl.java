package org.systa.food.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.systa.food.dao.UsersOtpDao;
import org.systa.food.dao.mapper.UsersOtpMapper;
import org.systa.food.dto.UsersOtp;
import org.systa.food.util.QueryConstants;

public class UsersOtpDaoImpl implements UsersOtpDao {
	
	private JdbcTemplate jdbcTemplate;

	@Override
	public void setJdbcTemplate(DataSource dataSource) {
		jdbcTemplate = new JdbcTemplate(dataSource);		
	}

	@Override
	public long createOtp(UsersOtp usersOtp) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(QueryConstants.SAVE_USERS_OTP_DETAILS, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, usersOtp.getOtpValue());
	          ps.setString(2,  usersOtp.getUuid());
	          ps.setString(3, usersOtp.getMobileNumber());
	          ps.setBoolean(4,  usersOtp.isActive());
	          ps.setTimestamp(5,  usersOtp.getCreateDate());
	          ps.setTimestamp(6, usersOtp.getUpdateDate());
	          return ps;
	        }, keyHolder);
		
	        return keyHolder.getKey().longValue();		
	}

	@Override
	public void updateOtpDetails(UsersOtp usersOtp) {
		jdbcTemplate.update(QueryConstants.UPDATE_USER_OTP_DETAILS, 
				new Object[] {usersOtp.getOtpValue(), usersOtp.isActive(), usersOtp.getUpdateDate(), usersOtp.getId()});
		
	}

	@Override
	public UsersOtp getOtpDetailsByUUID(String uuid) {
		try{
			return jdbcTemplate.queryForObject(QueryConstants.GET_OTP_DETAILS_BY_UUID,
					new Object[] {uuid},
					new UsersOtpMapper());
		}
		catch(IncorrectResultSizeDataAccessException e){
			return null;
		}
	}

	@Override
	public void deactivateOtp(Long id) {
		jdbcTemplate.update(QueryConstants.DEACTIVE_OTP, 
				new Object[] {0, id});
		
	}

}
