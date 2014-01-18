package com.library.test.dao;


import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.User;

public class UserDaoTest {

	UserDao userDao;
	Session session; 
			
	public UserDaoTest()
	{
		
	}
	
	@Before
	public void setUp()
	{
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.userDao = new UserDao(this.session);
	}
	
	@After
	public void tearDown()
	{
		this.session.close();
	}
	
	@Test
	public void testUserSave()
	{
		User user = new User("Sandarbh","Bhadauria","Student");
		this.userDao.saveOrUpdate(user);
		
		Assert.assertNotNull(userDao.getAll());
		
	}
	
}
