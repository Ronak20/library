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

}
