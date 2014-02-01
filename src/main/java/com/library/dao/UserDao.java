package com.library.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.model.User;

public class UserDao {

	private static Logger logger = Logger.getLogger(UserDao.class);
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

	public boolean isValid(String username, String pass) {
		logger.debug("isValid username : " + username + " pass  : " + pass);
		boolean isValid = false;

		String hql = "FROM User U WHERE U.username = :user_name";
		Query query = session.createQuery(hql);
		query.setParameter("user_name", username);
		List<User> userList = query.list();
		User user = userList.get(0);

		if (!user.equals(null)) {
			isValid = true;
			logger.debug("return isValid : " + isValid);
			return isValid;
		} else {
			logger.debug("return isValid : " + isValid);
			return isValid;
		}
	}

	public User getUserByName(String username) {
		logger.debug("isValid username : " + username);
		try {
			String hql = "FROM User U WHERE U.username = :user_name";
			Query query = session.createQuery(hql);

			query.setParameter("user_name", username);
			List<User> userList = query.list();
			User user = userList.get(0);
			logger.debug("return user : " + user);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug("return user : " + null);
			return null;
		}
	}

	public User getUserById(String aUserId) {
		try {
			String hql = "FROM User U WHERE U.userId = :user_id";
			Query query = session.createQuery(hql);

			query.setParameter("user_id", aUserId);
			List<User> userList = query.list();
			User user = userList.get(0);
			// Book bookInstance = session
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
