package com.library.test.http;

import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.servlet.PayFeesServlet;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class TC20 {

	private static Logger logger = Logger.getLogger(TC20.class);

	private Session session;
	private LoanService loanService;
	private String isbn;
	private String loanID;
	private String userID;
	private String bookID;
	private BookDao bookDao;
	private UserDao userDao;
	private BookService bookService;
	private LoanDao loanDao;

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp");
		UUID uuid = UUID.randomUUID();

		// add user
		session = HibernateUtil.getSessionFactory().openSession();
		userDao = new UserDao(session);
		User user = new User("fName" + uuid, "lName" + uuid, "uName" + uuid,
				"pWord" + uuid, Role.ADMIN);
		this.userID = userDao.saveOrUpdate(user);

		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);

		this.isbn = "isbn" + uuid;
		Book book = new Book("bookname" + uuid, isbn, 10);
		this.bookID = bookDao.saveOrUpdate(book);

		// rewnwing loan
		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		loanService.addLoan(this.userID, this.bookID);
		this.loanID = loanDao.getLoanByUserIdBookId(userID, bookID).get(0)
				.getLoanId();
		bookService.decreaseCopies(this.bookID);
		loanService.renewLoan(loanID);
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
		session.close();
	}

	@Test
	public void testTC20PayFineAfterRenewal() throws InterruptedException,
	IOException, SAXException {
		logger.info("Entered testTC19PayFine");
		// Thread.sleep(4*60*1000);
		logger.info(" loanID : " + loanID + " bookID : " + bookID
				+ " userID : " + userID);
		WebConversation conversation = new WebConversation();
		WebRequest requestLoanRenewal = new GetMethodWebRequest(
				Constant.getRenewLoanUrl(loanID, userID));
		// renew the loan
		conversation.getResponse(requestLoanRenewal);
		// let the loan expire
		Thread.sleep(4 * 60 * 1000);
		// pay the late fee
		WebRequest requestPayFine = new GetMethodWebRequest(
				Constant.getPayFeeUrl(loanID, userID));
		conversation.getResponse(requestPayFine);
		Loan loan = loanDao.getLoanByUserIdBookId(userID, bookID).get(0);
		logger.debug(loan);
		logger.info("loanID:" + loan.getLoanId() + "has latfee: "
				+ loan.getLateFee() + "");
		Assert.assertTrue(loan.getIsLateFeePaid());
		Assert.assertEquals(0, loan.getLateFee());
		logger.info("Exited testTC19PayFine");
	}

}
