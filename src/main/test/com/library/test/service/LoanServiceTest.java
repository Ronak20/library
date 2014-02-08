package com.library.test.service;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.config.HibernateUtil;
import com.library.dao.LoanDao;
import com.library.model.Loan;
import com.library.service.LoanService;

public class LoanServiceTest {

	LoanService loanService;
	LoanDao loanDao;
	Session session;
	
	@Before
	public void setUp() throws Exception {
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.loanDao = new LoanDao(this.session);
		this.loanService = new LoanService(this.loanDao);
	}

	@After
	public void tearDown() throws Exception {
		this.session.close();
	}

}
