package com.library.dao;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.exception.dao.NotFoundException;
import com.library.model.User;

public class UserDao {

	private static Logger logger = Logger.getLogger(UserDao.class);
	Session session;

	public UserDao(Session session) {
		this.session = session;
	}

	public String saveOrUpdate(User user) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(user);
			tx.commit();
			session.refresh(user);
			return  user.getUserId();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return null;
	}

	public User get(String username, String pass) throws NotFoundException {
		logger.info("get username : " + username + " pass  : " + pass);
		
		String hql = "FROM User U WHERE U.username = :user_name and U.password = :pass_word";
		Query query = session.createQuery(hql);
		query.setParameter("user_name", username);
		query.setParameter("pass_word", pass);
		List<User> userList = query.list();
		
		if (userList != null && !userList.isEmpty()) {
			User authorizedUser = userList.get(0);

			if (!authorizedUser.equals(null)) {
				logger.info("return get : " + authorizedUser);
				return authorizedUser;
			} else {
				logger.error(new NotFoundException("user not found"));
				throw new NotFoundException("user not found");
			}
		} else {
			logger.error(new NotFoundException("user not found"));
			throw new NotFoundException("user not found");
		}
	}

	public User getUserByName(String username) {
		logger.info("isValid username : " + username);
		try {
			String hql = "FROM User U WHERE U.username = :user_name";
			Query query = session.createQuery(hql);

			query.setParameter("user_name", username);
			List<User> userList = query.list();
			User user = userList.get(0);
			logger.info("return user : " + user);
			return user;
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("return user : " + null);
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
		logger.info("user : "+user);
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(user);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		logger.info("return");
	}
}
