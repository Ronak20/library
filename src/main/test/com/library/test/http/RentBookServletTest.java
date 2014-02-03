package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class RentBookServletTest extends TestCase {
	private static Logger logger = Logger.getLogger(RentBookServletTest.class);

	public RentBookServletTest(String s) {
		super(s);
	}

	public void setUp() throws Exception {
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

	public void tearDown() throws Exception {

	}

	public void testRentBookListBooks() throws Exception {
		logger.info("Entering testRentBookListBooks");
		WebConversation conversation = new WebConversation();
		//WebRequest request = new GetMethodWebRequest(Constant.USERBOOKS_GET_URL);
		//WebResponse response = conversation.getResponse(request);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		
		
		
		WebRequest requestBookList = new GetMethodWebRequest(Constant.USERBOOKS_GET_URL);
		WebResponse responseBookList = conversation.getResponse(requestBookList);
		WebTable userBookListTable = responseBookList.getTableWithID("userBookListTable");
		//TableCell tableCell = userBookListTable.getTableCellWithID("isbn" + "");
		/*
		 * Testing number of books for user panel when clicked "RentBook"
		 * FIXME : change '6' to the actualy number of books you have in your database
		 */
		
		requestBookList.setParameter("currentUser", "20");
		

		
		assertEquals("coloumn count",bookDao.getAll().size(), userBookListTable.getRowCount() -1);
		logger.info("Getting Row Count" + " = " + userBookListTable.getRowCount());
		logger.info("Exited testRentBook");
		session.close();
		responseBookList.close();
		
	}

	public void testRentBookResult() throws Exception {
		logger.info("Entered testTC3AddTitle");
		WebConversation conversation = new WebConversation();
		//WebRequest request = new GetMethodWebRequest(Constant.RENT_BOOK_URL);
		//WebResponse response = conversation.getResponse(request);
		
		//request.setParameter("currentUser", "20");
		;
		
		WebRequest requestBookList = new GetMethodWebRequest(Constant.RENT_BOOK_URL);
		WebResponse responseBookList = conversation.getResponse(requestBookList);
		WebTable userRentedBooksTable = responseBookList.getTableWithID("rentedBooks");
		
		requestBookList.setParameter("auser", "4");
		requestBookList.setParameter("bookid", "5");
		//TableCell tableCell = userBookListTable.getTableCellWithID("isbn" + "");
		/*
		 * Testing whether the book was added and shown on the user panel
		 * FIXME : 
		 */
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		
		
		int aRow = loanDao.getAll().size();
		
		
		System.out.println(aRow);
		
		assertEquals("Testing if book was added","5", userRentedBooksTable.getCellAsText(aRow, 0));
		logger.info("Getting Row Count" + " = " + userRentedBooksTable.getTableCellWithID(requestBookList.getParameter("bookid")));

		
		
		
		logger.info("Exited testRentBook");
		responseBookList.close();
		session.close();
	}
	
}
