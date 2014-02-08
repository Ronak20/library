package com.library.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;

import com.library.config.LogConstant;
import com.library.config.MessageConstant;
import com.library.dao.LoanDao;
import com.library.exception.dao.NotFoundException;
import com.library.exception.service.ConstraintViolationException;
import com.library.model.Loan;

public class LoanService {

	private static Logger logger = Logger.getLogger(LoanService.class);
	LoanDao loanDao;

	public LoanService(LoanDao loanDao) {
		this.loanDao = loanDao;
	}

	/**
	 * @param loan
	 *            method to add and update loan
	 * @throws NotFoundException
	 */
	public String renewLoan(String loanid) {
		logger.info(LogConstant.ENTERED + "renewLoan");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Loan loan = getLoanByID(loanid);

		Date expiryDate = null;
		try {
			expiryDate = sdf.parse(loan.getExpiryDate().toString());
		} catch (ParseException e) {
			logger.error("Cannot parse expiry date", e);
		}

		logger.debug(expiryDate);
		logger.debug(new Date());
		logger.debug(loan.getExpiryDate().compareTo(new Date()));

		if (expiryDate.compareTo(new Date()) > 0) {
			if (loan.getRenewalCount() < 3 && loan.getIsLateFeePaid()) {
				loanDao.renewLoan(loanid);
				logger.info(LogConstant.RETURN + "Renewed");
				return "Renewed";
			} else {
				logger.info(LogConstant.RETURN + "Unallowed");
				return "FeePending";
			}
		} else {
			logger.info(LogConstant.RETURN + "Expired");
			return "Expired";
		}
	}

	public void payFees(String loanId) {
		loanDao.payFees(loanId);
	}

	public void delete(String userid, String bookid) {
		this.loanDao.delete(userid, bookid);
	}

	public void deleteLoanByLoanID(String loanid) {
		loanDao.deleteById(loanid);
	}

	public Loan getLoanByID(String loanid) {
		return loanDao.getLoanByID(loanid);
	}

	public void delete(Loan loan) {
		this.loanDao.delete(loan);
	}

	public List<Loan> getExpiredLoanByUserId(String userId) {
		List<Loan> loanList = this.loanDao.getExpiredLoanByUserId(userId);
		return loanList;
	}
	public boolean userHasLateFee(String userId){
		return this.loanDao.getLateFeeLoanByUserId(userId);
	}

	public List<Loan> getLoanByUserId(String userId) {
		List<Loan> loanList = this.loanDao.getLoanByUserId(userId);
		return loanList;
	}

	public List<Loan> getLoanByBookId(String bookId) {
		List<Loan> loanList = this.loanDao.getLoanByBookId(bookId);
		return loanList;
	}

	public Boolean OkayToDelete(String userId) {
		if (getLoanByUserId(userId).size() > 0)
			return false;
		else
			return true;
	}

	public Boolean OkayToDeleteBook(String bookId) {
		List<Loan> loanList = getLoanByBookId(bookId);
		if (loanList != null) {
			if (loanList.size() > 0)
				return false;
			else
				return true;
		} else {
			return false;
		}
	}

	public boolean addLoan(String userId, String bookId)
			throws ConstraintViolationException, NotFoundException {

		Loan newLoan = new Loan(userId, bookId);
		String loanId;

		// check if user has any late fee, if yes then don't add loan
		if (loanDao.getLateFeeLoanByUserId(userId)) {
			logger.error(LogConstant.ERROR
					+ MessageConstant.USER_LATE_FEE_MESSAGE);
			throw new ConstraintViolationException(
					MessageConstant.USER_LATE_FEE_MESSAGE);
		} else {
			loanId = loanDao.saveOrUpdate(newLoan);
			logger.info(LogConstant.RETURN + true);
			return true;
		}

	}

	public int updateLateFeesByUser(String userid) {
		return loanDao.updateLateFeesByUser(userid);
	}

	public int updateLateFees() {
		return loanDao.updateLateFees();
	}
	
	public List<Loan> getLoanByUserIdBookId(String userid, String bookid) {
		return loanDao.getLoanByUserIdBookId(userid, bookid);
	}

}
