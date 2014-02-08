package com.library.test.http;

import static org.junit.Assert.*;

import java.util.Calendar;

import javax.servlet.http.HttpSession;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

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
import com.library.servlet.UnrentBook;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import com.meterware.servletunit.InvocationContext;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class TC25 extends TestCase {
	private static Logger logger = Logger.getLogger(TC3.class);
	private String bookName = "MyBookName"+ System.currentTimeMillis();
	private String isbn = "MyBookisbn"+ System.currentTimeMillis();
	private String bookid="";

	public TC25(String s) {
		super(s);
	}
	
	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp for TC 25 Delete book with multiple copies");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		HttpUnitOptions.setScriptingEnabled(false);
		WebResponse response = conversation.getResponse(request);
		logger.debug("Login Page : \n" + response.getText());
		WebForm loginForm = response.getFormWithID("loginForm");
		loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		loginForm.submit(submitButton);
		logger.info("Exit setUp for TC 25 Delete book with multiple copies");
	}

	@After
	public void tearDown() throws Exception {
	}

	public void testDeleteBookWithMultipleCopies() throws Exception {
		logger.info("Entered TC25 ");
		User user;
		String parameterUserName = "MyUser" + System.currentTimeMillis();
		user = new User("TestFirstName","TestLastName",parameterUserName,"password",Role.STUDENT);
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 UserDao userDao = new UserDao(session);
		 UserService userService = new UserService(userDao);
		  userService.saveOrUpdate(user);
		 logger.info("User added"+ user.getUsername());
		 BookDao bookDao = new BookDao(session);
		 BookService bookService = new BookService(bookDao);
		 
		 Book book = new Book(null,bookName,isbn,2);//created 2 copies
		 bookService.saveOrUpdate(book);
		 bookid = book.getBookid();
		 logger.info("Bookid created is: "+bookid);
		// now create loan for this user
		

			logger.info("trying to delete the bookiD: "+bookid);
			WebConversation conversation = new WebConversation();
		 WebRequest requestDeleteBook = new GetMethodWebRequest(
				 Constant.BOOK_DELETE_URL+bookid);
		WebResponse responseGetUser = conversation.getResponse(requestDeleteBook);
			WebTable bookListUpdatedTable = responseGetUser.getTableWithID("bookListTable");
			TableCell tableUpdatedCell = bookListUpdatedTable.getTableCellWithID(bookid);
		assertNull(tableUpdatedCell);
		session.close();
		logger.info("Exited TC 25 ");
	}
}
