package com.library.dao;

import org.hibernate.Session;

import com.library.model.User;

public class UserDao {
	
	Session session;
	
	public UserDao(Session session)
	{
		this.session = session;
	}
	
	public void saveOrUpdateClaim(User user) {
		session.saveOrUpdate(user);
	}	
}
