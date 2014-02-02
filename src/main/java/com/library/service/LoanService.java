package com.library.service;

import java.util.List;

import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Loan;

public class LoanService {

	LoanDao loanDao;

	public LoanService(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

	/**
	 * @param loan
	 *            method to add and update loan
	 */
	public void save(Loan loan) {
		List<Loan> latePaymentLoans = this.loanDao.getExpiredLoanByUserId(loan
				.getUserId());

		if (latePaymentLoans == null) {

			Loan dbLoan = this.loanDao.getLoanByUserIdBookId(loan.getUserId(),
					loan.getBookId());
			int renewalCount = dbLoan.getRenewalCount();
			if (renewalCount <= 3) {
				this.loanDao.saveOrUpdate(loan);
			} else {
				throw new RuntimeException("Cannot renew anymore: "
						+ dbLoan.toString());
			}

		} else {
			throw new RuntimeException("Loan payment Pending : "
					+ latePaymentLoans.toString());
		}
	}

	public void renewLoan(String userid, String bookid) {
		// get if there is any loan using getExpiredLoanByUserId
		// check if user has any pending late fees using getExpiredLoanByUserId
		// check renewal count loan object of first comment
		// set new time and update the time and renewal count
	}

	/*
	 * public List<Loan> getAll() { List<Loan> loanList = this.loanDao.getAll();
	 * return loanList; }
	 * 
	 * public Loan getLoanByID(String loanid) { Loan loan =
	 * this.loanDao.getLoanByID(loanid); return loan; }
	 * 
	 * public Loan getLoanByUserIdBookId(String userid, String bookid) { Loan
	 * loan = this.loanDao.getLoanByUserIdBookId(userid, bookid); return loan; }
	 */

	public void delete(String userid, String bookid) {
		this.loanDao.delete(userid, bookid);
	}

	public void delete(Loan loan) {
		this.loanDao.delete(loan);
	}

	public List<Loan> getExpiredLoanByUserId(String userId) {
		List<Loan> loanList = this.loanDao.getExpiredLoanByUserId(userId);
		return loanList;
	}

	public List<Loan> getLoanByUserId(String userId) {
		List<Loan> loanList = this.loanDao.getLoanByUserId(userId);
		return loanList;
	}

	public Boolean OkayToDelete(String userId) {
		if (getLoanByUserId(userId).size() > 0)
			return false;
		else
			return true;
	}
	
	public Boolean addLoan(String userId, String bookId) {
		
		
		/*for ( Loan ln : getLoanByUserId(userId))
		if(!ln.getIsLateFeePaid())
		{
			return false;
		}
		else
		{*/
			Loan newLoan = new Loan(userId, bookId);
			//loanDao.addLoan(userId, bookId);
			loanDao.saveOrUpdate(newLoan);
			System.out.println("Book was successfully rented , Loan Id: ");
			return true;
		//}
	
		
		/*if (getLoanByUserId(userId).size() > 0)
			return false;
		else
	
			return true;*/
	}
	

}
