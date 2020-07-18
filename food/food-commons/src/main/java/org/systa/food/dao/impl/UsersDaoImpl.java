package org.systa.food.dao.impl;

import java.sql.PreparedStatement;
import java.sql.Statement;

import javax.sql.DataSource;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.systa.food.dao.UsersDao;
import org.systa.food.dao.mapper.UserMapper;
import org.systa.food.dto.Users;
import org.systa.food.util.QueryConstants;

public class UsersDaoImpl implements UsersDao {

	private JdbcTemplate jdbcTemplate;
	
	public void setJdbcTemplate(DataSource dataSource){
		jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	public Users getUserByMobileNumber(String mobileNumber) {
		return jdbcTemplate.queryForObject(QueryConstants.GET_USER_BY_MOBILE_QUERY,
				new Object[] {mobileNumber},
				new UserMapper());
	}

	public long saveUser(Users user) {
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(connection -> {
	        PreparedStatement ps = connection
	          .prepareStatement(QueryConstants.SAVE_USER, Statement.RETURN_GENERATED_KEYS);
	          ps.setString(1, user.getMobileNumber());
	          ps.setBoolean(2,  user.getActive());
	          ps.setTimestamp(3,  user.getCreateDate());
	          ps.setTimestamp(4, user.getUpdateDate());
	          return ps;
	        }, keyHolder);
		
	        return keyHolder.getKey().longValue();
	}

	public boolean updateUser(Users user) {
		jdbcTemplate.update(QueryConstants.UPDATE_USER_BY_ID, 
				new Object[] {user.getEmail(), user.getName(), user.getFullName(), user.getUpdateDate(), user.getId()});
		return true;
	}

	public Users getUserById(Long id) {
		try{
			return jdbcTemplate.queryForObject(QueryConstants.GET_USER_BY_ID,
					new Object[] {id},
					new UserMapper());
		}
		catch(IncorrectResultSizeDataAccessException e){
			return null;
		}
		catch(DataAccessException e){
			return null;
		}
	}

}
