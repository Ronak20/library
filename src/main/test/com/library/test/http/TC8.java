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

public class TC8 {

	private static Logger logger = Logger.getLogger(TC8.class);

	private Session session;
	private String isbn;
	private String loanID;
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
		userDao = new UserDao(session);
		userService = new UserService(userDao);
		user = new User("fName" + uuid, "lName" + uuid, "uName" + uuid, "pWord"
				+ uuid, Role.ADMIN);
		this.userID = userDao.saveOrUpdate(user);

		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);

		this.isbn = "isbn" + uuid;
		Book book = new Book("bookname" + uuid, isbn, 10);
		this.bookID = bookDao.saveOrUpdate(book);

		loanDao = new LoanDao(session);
		logger.info(LogConstant.EXITED);
	}

	@After
	public void tearDown() throws Exception {
		loanService.delete(this.userID, this.bookID);
		bookService.deleteBook(this.bookID);
		userService.deleteUser(this.userID);
	}

	@Test
	public void testBorrowMultipleCopies() throws InterruptedException,
	IOException, SAXException {
		logger.info("Entered testBorrowMultipleCopies");
		logger.info(" loanID : " + loanID + " bookID : " + bookID
				+ " userID : " + userID);

		WebConversation conversation = new WebConversation();
		WebResponse response = conversation.getResponse(Constant.RENT_BOOK_URL);
		WebResponse response2 = conversation.getResponse(Constant.RENT_BOOK_URL);
		WebResponse response3 = conversation.getResponse(Constant.RENT_BOOK_URL);
		
		List<Loan> loanList = loanDao.getLoanByUserIdBookId(userID, bookID);
		logger.debug(loanList);

		Assert.assertNotNull(loanList);
		Assert.assertSame(3, loanList.size());

		logger.info("Exited testBorrowMultipleCopies");
	}

}
