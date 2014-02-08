package com.library.test.http;

import java.io.IOException;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.config.LogConstant;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;

public class TC12 {

	private static Logger logger = Logger.getLogger(TC12.class);

	private Session session;

	private String isbn;
	private String loanId;
	private String userID;
	private String bookID;

	private BookDao bookDao;
	private UserDao userDao;
	private LoanDao loanDao;

	private BookService bookService;
	private LoanService loanService;
	private UserService userService;
	private User user;

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp");
		UUID uuid = UUID.randomUUID();

		// add user
		session = HibernateUtil.getSessionFactory().openSession();

		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);

		userDao = new UserDao(session);
		userService = new UserService(userDao, loanDao);
		user = new User("fName" + uuid, "lName" + uuid, "uName" + uuid, "pWord"
				+ uuid, Role.ADMIN);
		this.userID = userDao.saveOrUpdate(user);

		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao, loanDao);

		this.isbn = "isbn" + uuid;
		Book book = new Book("bookname" + uuid, isbn, 10);
		this.bookID = bookDao.saveOrUpdate(book);

		loanService.addLoan(this.userID, this.bookID);
		loanId = this.loanService
				.getLoanByUserIdBookId(this.userID, this.bookID).get(0)
				.getLoanId();

		loanService.renewLoan(loanId);
		loanService.renewLoan(loanId);
		loanService.renewLoan(loanId);
		
		logger.info(LogConstant.EXITED);
	}

	@After
	public void tearDown() throws Exception {
		loanService.delete(this.userID, this.bookID);
		bookService.deleteBook(this.bookID);
		userService.delete(this.user);
		session.close();
	}

	@Test
	public void testRenewAfterMaxRenewalCount() throws InterruptedException,
			IOException, SAXException {
		logger.info("Entered testRenewAfterMaxRenewalCount");

		WebConversation conversation = new WebConversation();
		WebRequest requestBookList = new GetMethodWebRequest(
				Constant.getRenewLoanUrl(this.loanId, this.userID));
		conversation.getResponse(requestBookList);

		Loan loan = this.loanService.getLoanByID(this.loanId);
		session.refresh(loan);
		logger.debug(loan.toString());
		int renewalCount = loan.getRenewalCount();
		Assert.assertSame(3, renewalCount);

		logger.info("Exited testRenewAfterMaxRenewalCount");
	}
}
