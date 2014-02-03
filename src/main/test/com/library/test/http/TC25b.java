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
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class TC25b {

	private Session session;
	private UserDao userDao;
	private BookDao bookDao;
	private LoanService loanService;
	private BookService bookService;
	private String userID;
	private String bookID;
	private String loanID;
	private String loanID2;
	private String isbn;

	private static Logger logger = Logger.getLogger(TC25b.class);

	@Before
	public void setUp() throws Exception {

		UUID uuid = UUID.randomUUID();
		
		// add user
		session = HibernateUtil.getSessionFactory().openSession();
		userDao = new UserDao(session);
		User user = new User("fName"+uuid, "lName"+uuid, "uName"+uuid, "pWord"+uuid, Role.ADMIN);
		this.userID = userDao.saveOrUpdate(user);

		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);
		
		this.isbn = "isbn" + uuid;
		Book book = new Book("bookname" + uuid, isbn, 10);
		this.bookID = bookDao.saveOrUpdate(book);

		book.setCopies(20);
		System.out.println(book.getCopies());
		this.bookID = bookDao.saveOrUpdate(book);
		book.setCopies(100);
		System.out.println(book.getCopies());
		this.bookID = bookDao.saveOrUpdate(book);
		
		LoanDao loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		this.loanID = loanService.addLoan(this.userID, this.bookID);
		bookService.increaseCopies(this.bookID);
		this.loanID2 = loanService.addLoan(this.userID, this.bookID);
		bookService.increaseCopies(this.bookID);
		
		loanDao.deleteById(this.loanID);
		bookService.decreaseCopies(this.bookID);

		logger.info("Entered setUp");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		WebResponse response = conversation.getResponse(request);
		logger.debug("Login Page : \n" + response.getText());
		WebForm loginForm = response.getFormWithID("loginForm");
		loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		loginForm.submit(submitButton);
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
		session.close();
	}

	@Test
	public void testTC25RemoveTitleWithMultipleCopies() throws IOException, SAXException {
		logger.info("Entered testTC25bRemoveTitleWithMultipleCopies");
		logger.info("checking deleted book");
		WebConversation conversation = new WebConversation();
		WebRequest requestGetBook = new GetMethodWebRequest(
				Constant.BOOK_DELETE_URL + this.bookID);
		WebResponse responseGetBook = conversation.getResponse(requestGetBook);
		WebTable bookListUpdatedTable = responseGetBook.getTableWithID("bookListTable");
		TableCell tableUpdatedCell = bookListUpdatedTable.getTableCellWithID(this.isbn);
		Assert.assertNotNull(tableUpdatedCell);
		logger.debug("Updated copies : " + tableUpdatedCell.getText());
		logger.info("Exited testTC25bRemoveTitleWithMultipleCopies");
	}

}
