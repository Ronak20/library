package com.library.test.dao;

import java.util.Date;

import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.library.config.HibernateUtil;
import com.library.dao.LoanDao;
import com.library.exception.dao.NotFoundException;
import com.library.model.Loan;

public class LoanDaoTest {

	LoanDao loanDao;
	Session session;

	public LoanDaoTest() {

	}

	@Before
	public void setUp() {
		this.session = HibernateUtil.getSessionFactory().openSession();
		this.loanDao = new LoanDao(this.session);
	}

	@After
	public void tearDown() {
		this.session.close();
	}

	@Test
	public void testLoanSave() throws NotFoundException {
		Loan loan = new Loan("4", "1", new Date(
				System.currentTimeMillis() + 5 * 60 * 1000), 0, 0, false);
		this.loanDao.saveOrUpdate(loan);
		Assert.assertNotNull(loanDao.getAll());
	}

	@Test
	public void testLoanDelete() throws NotFoundException {
		Loan loan = new Loan("5", "5", new Date(
				System.currentTimeMillis() + 5 * 60 * 1000), 0, 0, false);
		this.loanDao.saveOrUpdate(loan);
		this.loanDao.delete("5", "5");
	}

	@Test
	public void testLoanDeletebyId() throws NotFoundException {
		Loan loan = new Loan("7", "3", new Date(
				System.currentTimeMillis() + 5 * 60 * 1000), 0, 0, false);

		this.loanDao.saveOrUpdate(loan);
		// this.loanDao.deleteById("2");
	}

	@Test
	public void testLateFeeLoanByUserId() {
		Assert.assertTrue(this.loanDao.getLateFeeLoanByUserId(18 + ""));
	}

	@Test
	public void testUpdateLateFees() {
		loanDao.updateLateFeesByUser(2 + "");
	}
}
