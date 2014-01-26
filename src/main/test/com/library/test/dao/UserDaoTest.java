package com.library.test.dao;


import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.Role;
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
		User user = new User("Sultan","Eid","sultan","password",Role.STUDENT);
		this.userDao.saveOrUpdate(user);
		
		Assert.assertNotNull(userDao.getAll());
	}
	
	@Test
	public void testUserDelete()
	{
		User user = new User("2");
		this.userDao.delete(user);
	}
	
	@Test
	public void getUserById()
	{
		
		User user = new User("getFirstTest","getLastTest","getByUNTest","sompass",null);
		this.userDao.saveOrUpdate(user);
		
		
		Assert.assertSame(user, this.userDao.getBookByName("getByUNTest"));
		
		userDao.delete(user);
	}
	
}
