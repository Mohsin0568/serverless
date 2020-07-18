package org.systa.food.dao;

import javax.sql.DataSource;

public interface Commons {

	void setJdbcTemplate(DataSource dataSource);
}
