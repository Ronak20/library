package com.library.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.model.Loan;

public class LoanDao {

	Session session;

	public LoanDao(Session session) {
		this.session = session;
	}

	public void saveOrUpdate(Loan loan) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.saveOrUpdate(loan);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}

	public List<Loan> getAll() {
		List<Loan> loanList = session.createCriteria(Loan.class).list();
		return loanList;
	}

	public Loan getLoanByID(String loanid) {
		String hql = "FROM Loan L WHERE L.loanId = :loanid";
		Query query = session.createQuery(hql);
		query.setParameter("loanid", loanid);
		List<Loan> loanList = query.list();
		Loan loan = loanList.get(0);
		return loan;
	}

	public Loan getLoanByUserIdBookId(String userid, String bookid) {
		String hql = "FROM Loan L WHERE L.userId = :userid and L.bookId= :bookid";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userid);
		query.setParameter("bookid", bookid);

		List<Loan> loanList = query.list();
		Loan loan = loanList.get(0);
		System.out.println(loan.toString());
		return loan;
	}
	
	public Loan getLastLoanByUserId(String userid) {
		String hql = "FROM Loan L WHERE L.userId = :userid";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userid);
		

		List<Loan> loanList = query.list();
		Loan loan = loanList.get(0);
		System.out.println(loan.toString());
		return loan;
	}

	public void delete(String userid, String bookid) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "delete from Loan where userId= :userid AND bookId= :bookid";
			Query query = session.createQuery(hql);
			query.setString("userid", userid);
			query.setString("bookid", bookid);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}
	
	public void deleteById(String aLoanId) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "DELETE FROM Loan WHERE loanId = :loanid";
			Query query = session.createQuery(hql);
			query.setString("loanid", aLoanId);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		
		
	}

	public void delete(Loan loan) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			session.delete(loan);
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
	}

	public List<Loan> getExpiredLoanByUserId(String userId) {
		String hql = "FROM Loan L WHERE L.userId =:userid and L.expiryDate <:currentDate";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		query.setDate("currentDate", new Date());
		List<Loan> loanList = query.list();
		return loanList;
	}
	
	public List<Loan> getLoanByUserId(String userId) {
		String hql = "FROM Loan L WHERE L.userId = :userid";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		List<Loan> loanList = query.list();
		return loanList;
	}
	
	public List<Loan> getLoanByUserIdIsLateFee(String userId,boolean isLateFee) {
		String hql = "FROM Loan L WHERE L.isLateFeePaid = :isLateFee";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		query.setParameter("isLateFee", isLateFee);
		List<Loan> loanList = query.list();
		return loanList;
	}
}
