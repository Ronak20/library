package com.library.test.http;

import java.util.UUID;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.Assert;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;

public class TC10 extends TestCase {

	private static Logger logger = Logger.getLogger(TC10.class);

	String bookId;
	String userId;
	String loanId;

	UserService userService;
	BookService bookService;
	LoanService loanService;

	LoanDao loanDao;
	UserDao userDao;
	BookDao bookDao;

	private User user;

	private Session session;

	public TC10(String s) {
		super(s);
	}

	public void setUp() throws Exception {
		logger.info("Entered setUp");
		UUID uuid = UUID.randomUUID();
		session = HibernateUtil.getSessionFactory().openSession();
		loanDao = new LoanDao(session);
		userDao = new UserDao(session);

		this.userService = new UserService(userDao, loanDao);
		user = new User("firstName" + uuid, "lastName" + uuid, "username"
				+ uuid, "pWord" + uuid, Role.STUDENT);
		userId = this.userService.saveOrUpdate(user);

		Book book = new Book("bookName" + uuid, "isbn" + uuid, 10);
		bookDao = new BookDao(session);

		loanService = new LoanService(loanDao);

		bookService = new BookService(bookDao, loanDao);
		bookId = bookService.saveOrUpdate(book);

		loanService.addLoan(userId, this.bookId);
		loanId = this.loanService.getLoanByUserIdBookId(userId, bookId).get(0)
				.getLoanId();

		bookService.decreaseCopies(this.bookId);

		logger.info("Exited setUp");
	}

	public void tearDown() throws Exception {
		loanService.deleteLoanByLoanID(loanId);
		bookService.deleteBook(bookId);
		userService.delete(user);
		session.close();
	}

	public void testRenewBook() throws Exception {
		logger.info("Entering testRenewBook");

		WebConversation conversation = new WebConversation();
		WebRequest requestBookList = new GetMethodWebRequest(
				Constant.getRenewLoanUrl(this.loanId, this.userId));
		conversation.getResponse(requestBookList);
		session.refresh(this.loanDao.getLoanByID(this.loanId));	
		Assert.assertEquals(1, this.loanDao.getLoanByID(this.loanId).getRenewalCount());

		logger.info("Exited testRenewBook");
	}
}
