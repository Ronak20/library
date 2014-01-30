package com.library.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import java.sql.*;
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
		Connection conn = null;
		ResultSet result= null;
		Statement st = null;
		
		String searchQuery =
	               "select * from user where username='"
	                        + aUsername
	                        + "' AND password='"
	                        + pass
	                        + "'";
		
		boolean isValid = false;
		
		try {
			//Hibernate Query
			String hql = "FROM User U WHERE U.username = :user_name";
			Query query = session.createQuery(hql);
			
			query.setParameter("user_name", aUsername);
			
			//query.setParameter("user_password", pass); AND U.password = :user_password
			
			List<User> userList = query.list();
			User user = userList.get(0);
			
			System.out.println("");
			System.out.println(user.toString());
			System.out.println("");
			
			
			//regular direct Query (just testing things)
			/*
			conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/library", "root", "");
	         st=conn.createStatement();
	         result = st.executeQuery(searchQuery);	        
	         //boolean more = result.next();
	         
	         User user = null;//(User) result;
	         
	         user.setUsername(result.getString("username"));
	         user.setPassword(result.getString("password"));
	         
	         System.out.println("");
				System.out.println(user.toString());
				System.out.println("");
	         
	         */
			
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
	
	public User getUserByName(String aUsername){
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
	public User getUserById(String aUserId){
		try {
		String hql = "FROM User U WHERE U.userId = :user_id";
		Query query = session.createQuery(hql);
		
		query.setParameter("user_id", aUserId);
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
