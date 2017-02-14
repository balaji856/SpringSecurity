package com.sec.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.sec.bean.UserDetailsImpl;
import com.sec.bo.UserInfo;
import com.sec.dao.UserDAO;
@Service
public class UserDetailsServiceImpl implements UserDetailsService{

	@Autowired
	private UserDAO userDao; 
	
	@Override
	public UserDetails loadUserByUsername(String username)
			throws UsernameNotFoundException {
		
		UserInfo userInfo=null;
		UserDetailsImpl userDetailsImpl=null;
		
		userInfo=userDao.getUserInfo(username);
		if(userInfo!=null)
		{
			userDetailsImpl=new UserDetailsImpl(userInfo.getUsername(), userInfo.getPassword(), userInfo.getRole());
		}
		
		return userDetailsImpl;
	}
}
