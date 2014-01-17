package com.library.main;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.User;

public class Main {

	public static void main(String... args)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		UserDao userDao = new UserDao(session);
		
		User user = new User("Ronak","Chaudhari","Student");
		userDao.saveOrUpdateClaim(user);
		
		session.close();
	}
	
}
