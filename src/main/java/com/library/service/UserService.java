package com.library.service;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.User;

public class UserService {
	
	UserDao userDao;
	LoanDao loanDao;
	
	public UserService(UserDao userDao)
	{
		this.userDao = userDao;
	}
	
	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}
	
	public Boolean deleteUser(String userId, LoanDao loandao) {
		//admin should not be able to delete a user if he has outstanding lone
		
		LoanService loanService =  new LoanService(loandao);
		
		if(loanService.OkayToDelete(userId))
		{
			User user = userDao.getUserById(userId);
			userDao.delete(user);
			return true;
		}
		else
			return false;
		
	}
	
	public User getSessionUser(Object user)
	{
		User returnedUser = null;
		returnedUser = (User) user;
		
		
	return null;	
	}

	public User getUserbyUserID(String userId)
	{
		return userDao.getUserById(userId);
	}

}
