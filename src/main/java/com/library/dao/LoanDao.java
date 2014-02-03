package com.library.dao;


import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.model.Loan;
//import quicktime.std.clocks.TimeBase;

public class LoanDao {

	Session session;

	public LoanDao(Session session) {
		this.session = session;
	}

	public String saveOrUpdate(Loan loan) {
		Transaction tx = null;
		try {
			/*
			 * TODO map date with database fields
			 */
			
			Calendar cal = Calendar.getInstance();
			
			
			
			//Formatting the time to string so we can trace it 
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss");
			String utcTime = df.format(new Date());
			
			System.out.println(df.format(new Date()));
			
			System.out.println("printing UTC: " + utcTime);
			
			
			
			//setting loan time (this is indicating it is three minutes, we can make it modifiable later)
			loan.setLoanTime(60000*3);

			//calculating Expiry Time: current time plus 3 minutes (which is 60k ms * 3)
			Date expiryTime = new Date ( System.currentTimeMillis() + loan.getLoanTime());
			//loan.setExpiryDate(utcTime);
			
			//setting expiry time 
			loan.setExpiryDate(expiryTime);
			
			loan.setIsLateFeePaid(true);
			
			
			System.out.println(df.format(cal.getTime()));
			System.out.println("Adding this loan:" + loan.toString());
			
			
			tx = session.beginTransaction();
			session.saveOrUpdate(loan);
			session.flush();
			tx.commit();
			session.refresh(loan);
			return loan.getLoanId();
			
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();
		}
		return null;
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
	
	
	public void renewLoan (String aLoanId)
	{
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "UPDATE Loan SET renewalCount = renewalCount + 1 WHERE loanId= :loanid";
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
	
	public List<Loan> getLoanByBookId(String bookId) {
		String hql = "FROM Loan L WHERE L.bookId = :bookid";
		Query query = session.createQuery(hql);
		query.setParameter("bookid", bookId);
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

	public void addLoan(String userId, String bookId) {
		// TODO Auto-generated method stub
		/*String ts = new String("00:00:00");
		
		String hql = "INSERT INTO Loan (userId =:userid, bookId = :bookid)";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		query.setParameter("bookid", bookId);
		query.setParameter("ts", ts);
		
		List<Loan> loanList = query.list();
		query.executeUpdate();*/
		
		
	}
}
