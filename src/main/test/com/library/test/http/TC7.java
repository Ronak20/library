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
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class TC7 {

	private static Logger logger = Logger.getLogger(TC7.class);

	private Session session;
	private String isbn;
	private String isbn2;
	private String isbn3;
	private String userID;
	private String bookID;
	private String bookID2;
	private String bookID3;
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

		UUID uuid2 = UUID.randomUUID();
		this.isbn2 = "isbn" + uuid2;
		Book book2 = new Book("bookname" + uuid2, isbn2, 20);
		this.bookID2 = bookDao.saveOrUpdate(book2);

		UUID uuid3 = UUID.randomUUID();
		this.isbn3 = "isbn" + uuid3;
		Book book3 = new Book("bookname" + uuid3, isbn3, 30);
		this.bookID3 = bookDao.saveOrUpdate(book3);

		logger.info(LogConstant.EXITED);
	}

	@After
	public void tearDown() throws Exception {
		loanService.delete(this.userID, this.bookID);
		loanService.delete(this.userID, this.bookID2);
		loanService.delete(this.userID, this.bookID3);
		bookService.deleteBook(this.bookID);
		bookService.deleteBook(this.bookID2);
		bookService.deleteBook(this.bookID3);
		userService.delete(this.user);
		session.close();
	}

	@Test
	public void testBorrowMultipleCopies() throws InterruptedException,
			IOException, SAXException {
		logger.info("Entered testBorrowMultipleCopies");
		logger.info("bookID : " + bookID + " userID : " + userID);

		WebConversation conversation = new WebConversation();
		WebResponse response = conversation.getResponse(Constant
				.getRentBookUrl(bookID, userID));
		WebResponse response2 = conversation.getResponse(Constant
				.getRentBookUrl(bookID2, userID));
		WebResponse response3 = conversation.getResponse(Constant
				.getRentBookUrl(bookID3, userID));

		List<Loan> loanList = loanDao.getLoanByUserIdBookId(userID, bookID);
		logger.debug(loanList);

		Assert.assertNotNull(loanList);
		Assert.assertSame(1, loanList.size());

		List<Loan> loanList2 = loanDao.getLoanByUserIdBookId(userID, bookID2);
		logger.debug(loanList2);

		Assert.assertNotNull(loanList2);
		Assert.assertSame(1, loanList2.size());

		List<Loan> loanList3 = loanDao.getLoanByUserIdBookId(userID, bookID3);
		logger.debug(loanList3);

		Assert.assertNotNull(loanList3);
		Assert.assertSame(1, loanList3.size());

		logger.info("Exited testBorrowMultipleCopies");
	}

}
