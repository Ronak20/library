package com.library.service;

import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.exception.dao.NotFoundException;
import com.library.exception.service.AuthenticationException;
import com.library.exception.service.ConstraintViolationException;
import com.library.model.Loan;
import com.library.model.User;

public class UserService {
	
	private static final Logger logger = Logger.getLogger(UserService.class);

	UserDao userDao;
	LoanDao loanDao;

	public UserService(UserDao userDao) {
		this.userDao = userDao;
	}
	
	public UserService(UserDao userDao,LoanDao loanDao) {
		this.userDao = userDao;
		this.loanDao = loanDao;
	}

	public void saveOrUpdate(User user) {
		userDao.saveOrUpdate(user);
	}

	
	public void deleteUser(String userId) throws ConstraintViolationException {
		
		List<Loan> loanList = loanDao.getLoanByUserId(userId);
		
		if(loanList == null || loanList.isEmpty())
		{
			User user = new User(userId);
			userDao.delete(user);
		}
		else
		{
			logger.error("Cannot delete user which has loan");
			throw new ConstraintViolationException("Cannot delete user which has loan");
		}
	
	}
	
	public boolean checkPayment (String uid)
	{
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		
		List<Loan> loans = loanDao.getLoanByUserId(uid);
		
		for (Loan ln : loans )
		{
			if (!ln.getIsLateFeePaid())
			{
				return false;
			}
		}
		
		
		return true;
	}
	
	public User getSessionUser(Object user) {
		User returnedUser = null;
		returnedUser = (User) user;
		return null;
	}

	public User getUserbyUserID(String userId) {
		return userDao.getUserById(userId);
	}

	public User get(String username, String pass) throws AuthenticationException {
		try {
			return userDao.get(username, pass);
		} catch (NotFoundException notFoundException) {
			logger.error("Not autenticated", notFoundException);
			throw new AuthenticationException("Not autenticated",notFoundException);
		}
	}

	public User getUserByName(String username) {
		return userDao.getUserByName(username);
	}
}
