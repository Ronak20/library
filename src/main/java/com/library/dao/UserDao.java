package com.library.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.model.User;

public class UserDao {

	Session session;

	public UserDao(Session session) {
		this.session = session;
	}

	public void saveOrUpdate(User user) {
		System.out.println("saveOrUpdate : " + user.toString());
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

	public List<User> getAll() {
		List<User> userList = session.createCriteria(User.class).list();
		return userList;
	}
}
