package com.library.service;

import com.library.dao.UserDao;
import com.library.model.User;

public class UserService {
	
	UserDao userDao;
	
	public UserService(UserDao userDao)
	{
		this.userDao = userDao;
	}
	
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}
	
	public User getSessionUser(Object user)
	{
		User returnedUser = null;
		returnedUser = (User) user;
		
		
	return null;	
	}

	

}
