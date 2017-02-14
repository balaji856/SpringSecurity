package com.sec.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.sec.bo.UserInfo;

@Repository
public class UserDAO {
	
	String sql="select  u.username, u.pass, r.role FROM db.users u  inner join db.user_roles r WHERE  u.username=r.username and u.username=?";
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource)
	{
		jdbcTemplate=new JdbcTemplate(dataSource);
	}

	public UserInfo getUserInfo(String user)
	{
		return  jdbcTemplate.queryForObject(sql, new RowMapper<UserInfo>(){
			@Override
			public UserInfo mapRow(ResultSet rs, int index)
					throws SQLException {
				
				UserInfo userInfo=new UserInfo();
				userInfo.setUsername(rs.getString("u.username"));
				userInfo.setPassword(rs.getString("u.pass"));
				userInfo.setRole(rs.getString("r.role"));
				
				return userInfo;
			}
		},new Object[] {user});
	}
}
