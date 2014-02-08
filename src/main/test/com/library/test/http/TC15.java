package com.library.test.http;

import java.io.IOException;
import java.util.List;
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
import com.meterware.httpunit.WebResponse;

public class TC15 {

	private static Logger logger = Logger.getLogger(TC15.class);

	private Session session;
	private User user;

	private BookService bookService;
	private LoanService loanService;
	private UserService userService;

	private String isbn;
	private String loanID;
	private String userID;
	private String bookID;
	private String bookID2;

	private BookDao bookDao;
	private UserDao userDao;
	private LoanDao loanDao;

	@Before
	public void setUp() throws Exception {
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
		
		Book book2 = new Book("bookname2" + uuid, "isbn2" + uuid, 20);
		this.bookID2 = bookDao.saveOrUpdate(book2);

		loanService = new LoanService(loanDao);
		loanService.addLoan(this.userID, this.bookID);

		Loan loan = loanDao.getLoanByUserIdBookId(userID, bookID).get(0);

		this.loanID = loan.getLoanId();

		Thread.sleep(4 * 60 * 1000);
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
		loanService.deleteLoanByLoanID(loanID);
		bookService.deleteBook(bookID);
		bookService.deleteBook(bookID2);
		userService.delete(user);
		session.close();
	}

	@Test
	public void testTC15BorrowLoan() throws IOException, SAXException,
			InterruptedException {
		logger.info("Entered testTC15BorrowLoan");

		WebConversation conversation = new WebConversation();
		// logger.info(loanID + "   " + userID);
		WebRequest requestReturnBook = new GetMethodWebRequest(
				Constant.getRentBookUrl(bookID2, userID));
		WebResponse responseReturnBook = conversation
				.getResponse(requestReturnBook);

		// cannot delete loan until it pays fee
		List<Loan> loan = this.loanService.getLoanByUserIdBookId(bookID2, userID);
		Assert.assertSame(loan.size(),0 );

		logger.info("Exited testTC15BorrowLoan");
	}
}
