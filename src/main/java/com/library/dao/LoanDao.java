package com.library.dao;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.library.config.ConfigConstant;
import com.library.model.Loan;

public class LoanDao {

	Session session;

	public LoanDao(Session session) {
		this.session = session;
	}

	public String saveOrUpdate(Loan loan) {
		Transaction tx = null;
		try {
			
			Calendar cal = Calendar.getInstance();

			// Formatting the time to string so we can trace it
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String utcTime = df.format(new Date());

			// setting loan time (this is indicating it is three minutes, we can
			// make it modifiable later)
			loan.setLoanTime(ConfigConstant.LOAN_PERIOD);

			// calculating Expiry Time: current time plus 3 minutes (which is
			// 60k ms * 3)
			Date expiryTime = new Date(System.currentTimeMillis()
					+ loan.getLoanTime());
			// loan.setExpiryDate(utcTime);

			// setting expiry time
			loan.setExpiryDate(expiryTime);

			loan.setIsLateFeePaid(true);

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

	public void renewLoan(String aLoanId) {
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "UPDATE Loan SET renewalCount = renewalCount + 1,expiryDate =:ed WHERE loanId=:loanid and expiryDate >= current_timestamp() ";
			Query query = session.createQuery(hql);
			query.setString("loanid", aLoanId);
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			cal.setTime(new Date());
			cal.add(Calendar.MINUTE, ConfigConstant.RENEWAL_PERIOD);
			String newTime = df.format(cal.getTime());
			
			query.setString("ed", newTime);
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

	public boolean getLateFeeLoanByUserId(String userId) {
		String hql = "FROM Loan L WHERE L.userId =:userid and L.lateFee>0 and L.isLateFeePaid=false";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		List<Loan> lateFeeList = query.list();
		if (lateFeeList.size() > 0)
			return true;
		else
			return false;

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

	public List<Loan> getLoanByUserIdIsLateFee(String userId, boolean isLateFee) {
		String hql = "FROM Loan L WHERE L.isLateFeePaid = :isLateFee";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userId);
		query.setParameter("isLateFee", isLateFee);
		List<Loan> loanList = query.list();
		return loanList;
	}

	public void payFees(String loanId) {
		
		Transaction tx = null;
		try {
			tx = session.beginTransaction();
			String hql = "UPDATE Loan l SET l.isLateFeePaid = 1,l.lateFee=0 WHERE l.loanId= :loanid";

			Query query = session.createQuery(hql);
			query.setString("loanid", loanId);
			query.executeUpdate();
			tx.commit();
		} catch (Exception e) {
			e.printStackTrace();
			tx.rollback();

		}
	}

	public int updateLateFeesByUser(String userid) {

		String hql = "UPDATE Loan l set l.isLateFeePaid = 0,l.lateFee = 100"
				+ " WHERE userid = :userid and l.expiryDate <= current_timestamp()";
		Query query = session.createQuery(hql);
		query.setParameter("userid", userid);
		
		int result = query.executeUpdate();
		return result;
	}
	
	public int updateLateFees() {

		String hql = "UPDATE Loan l set l.isLateFeePaid = 0,l.lateFee = 100"
				+ " WHERE l.expiryDate <= current_timestamp()";
		Query query = session.createQuery(hql);
		
		int result = query.executeUpdate();
		return result;
	}
}
