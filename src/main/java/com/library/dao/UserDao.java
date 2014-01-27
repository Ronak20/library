package com.library.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.config.HibernateUtil;
import com.library.model.Book;
import com.library.model.User;

public class UserDao {

	Session session;

	public UserDao(Session session) {
		this.session = session;
	}

	public void saveOrUpdate(User user) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	
	public boolean isValid (String aUsername, String pass)
	{
		boolean isValid = false;
		
		try {
			String hql = "FROM User U WHERE U.username = :user_name AND U.password = :user_password";
			Query query = session.createQuery(hql);
			
			query.setParameter("user_name", aUsername);
			query.setParameter("user_password", pass);
			
			List<User> userList = query.list();
			User user = userList.get(0);
			
			
			System.out.println(user.toString());
			
			if (!user.equals(null))
			{
				isValid = true;
				return isValid;
			}
			
			else {
				return isValid;
			}
			
			} catch (Exception e) {
				e.printStackTrace();
				isValid = false; 
				return false;			
				}
		
		
	}
	
	public User getBookByName(String aUsername){
		try {
		String hql = "FROM User U WHERE U.username = :user_name";
		Query query = session.createQuery(hql);
		
		query.setParameter("user_name", aUsername);
		List<User> userList = query.list();
		User user = userList.get(0);
		//Book bookInstance = session
		System.out.println(user.toString());
		return user;
		} catch (Exception e) {
			e.printStackTrace();
			return null;			
			}
	}

	public List<User> getAll() {
		List<User> userList = session.createCriteria(User.class).list();
		return userList;
	}

	public void delete(User user) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();

			session.delete(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
}
