package com.example.lamda.commons.dao;

import javax.sql.DataSource;

public interface Commons {

	void setJdbcTemplate(DataSource dataSource);
}
