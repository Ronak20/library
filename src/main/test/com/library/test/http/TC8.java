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
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebResponse;

public class TC8 {

	private static Logger logger = Logger.getLogger(TC8.class);

	private Session session;
	private UserDao userDao;
	private LoanDao loanDao;
	private BookDao bookDao;

	private UserService userService;
	private LoanService loanService;
	private BookService bookService;

	private String userid1;
	private String userid2;
	private String userid3;
	
	private User user1;
	private User user2;
	private User user3;
	
	private String bookid;

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp");
		UUID uuid = UUID.randomUUID();

		session = HibernateUtil.getSessionFactory().openSession();

		userDao = new UserDao(session);
		loanDao = new LoanDao(session);
		loanService =  new LoanService(loanDao);
		userService = new UserService(userDao, loanDao);

		UUID uuid1 = UUID.randomUUID();
		user1 = new User("fName" + uuid1, "lName" + uuid1,
				"uName" + uuid1, "pWord" + uuid1, Role.STUDENT);
		this.userid1 = userService.saveOrUpdate(user1);
		user1.setUserId(userid1);

		UUID uuid2 = UUID.randomUUID();
		user2 = new User("fName" + uuid2, "lName" + uuid2,
				"uName" + uuid2, "pWord" + uuid2, Role.STUDENT);
		this.userid2 = userService.saveOrUpdate(user2);
		user2.setUserId(userid2);
		
		UUID uuid3 = UUID.randomUUID();
		user3 = new User("fName" + uuid3, "lName" + uuid3,
				"uName" + uuid3, "pWord" + uuid3, Role.STUDENT);
		this.userid3 = userService.saveOrUpdate(user3);
		user3.setUserId(userid3);
		
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao, loanDao);

		String isbn = "isbn" + uuid;
		Book book = new Book("bookname" + uuid, isbn, 10);
		this.bookid = bookService.saveOrUpdate(book);
	}

	@After
	public void tearDown() throws Exception {
		loanService.delete(this.userid1, this.bookid);
		loanService.delete(this.userid2, this.bookid);
		loanService.delete(this.userid3, this.bookid);
		bookService.deleteBook(this.bookid);
		userService.delete(this.user1);
		userService.delete(this.user2);
		userService.delete(this.user3);
		session.close();
	}

	@Test
	public void testMultipleUserBorrowingSameCopy() throws IOException, SAXException {
		
		logger.info("Entered testMultipleUserBorrowingSameCopy");
		
		WebConversation conversation = new WebConversation();
		WebResponse response = conversation.getResponse(Constant
				.getRentBookUrl(bookid, userid1));
		WebResponse response2 = conversation.getResponse(Constant
				.getRentBookUrl(bookid, userid2));
		WebResponse response3 = conversation.getResponse(Constant
				.getRentBookUrl(bookid, userid3));

		List<Loan> loanList = loanDao.getLoanByUserIdBookId(userid1, bookid);
		logger.debug(loanList.toString());

		Assert.assertNotNull(loanList);
		Assert.assertSame(1, loanList.size());

		List<Loan> loanList2 = loanDao.getLoanByUserIdBookId(userid1, bookid);
		logger.debug(loanList2.toString());

		Assert.assertNotNull(loanList2);
		Assert.assertSame(1, loanList2.size());

		List<Loan> loanList3 = loanDao.getLoanByUserIdBookId(userid1, bookid);
		logger.debug(loanList3.toString());

		Assert.assertNotNull(loanList3);
		Assert.assertSame(1, loanList3.size());

		logger.info("Exited testMultipleUserBorrowingSameCopy");

	}

}
