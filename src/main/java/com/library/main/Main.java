package com.library.main;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.User;
import com.library.service.UserService;

public class Main {

	public static void main(String... args)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();

		
		UserDao userDao = new UserDao(session);
		UserService userService = new UserService(userDao);
		
		User user = new User("Sultan","Eid","Student");
		userService.saveOrUpdate(user);
		
		session.close();
	}
	
}
