package com.library.test.notused;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.model.Loan;
import com.library.service.LoanService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
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
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		
		WebRequest requestBookList = new GetMethodWebRequest(Constant.USERBOOKS_GET_URL);
		WebResponse responseBookList = conversation.getResponse(requestBookList);
		WebTable userBookListTable = responseBookList.getTableWithID("userBookListTable");
		
		requestBookList.setParameter("currentUser", "20");
				
		assertEquals("coloumn count",bookDao.getAll().size(), userBookListTable.getRowCount() -1);
		logger.info("Getting Row Count" + " = " + userBookListTable.getRowCount());
		logger.info("Exited testRentBook");

		
	}

	public void testRentBookResult() throws Exception {
		logger.info("Entered testTC3AddTitle");
		WebConversation conversation = new WebConversation();
		
		WebRequest requestBookList = new GetMethodWebRequest(Constant.RENT_BOOK_URL);
		//user ID to rent
		String expectedUid = "4";
		//book ID to rent
		String expectedBid = "82";
		
		
		requestBookList.setParameter("auser", expectedUid);
		requestBookList.setParameter("bookid", expectedBid);
		WebResponse responseBookList = conversation.getResponse(requestBookList);
		WebTable userRentedBooksTable = responseBookList.getTableWithID("rentedBooks");
		
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);
		Loan ln = new Loan (requestBookList.getParameter("auser"),requestBookList.getParameter("bookid"));
		ls.addLoan(requestBookList.getParameter("auser"), requestBookList.getParameter("bookid"));
		
		
		
		
		int aRow = loanDao.getAll().size();
		
		
		System.out.println(aRow);
		
		assertEquals("Testing if book was added",expectedBid, responseBookList.getTableWithID("rentedBooks").getTableCellWithID(requestBookList.getParameter("82")).getID());
		logger.info("Getting Row Count" + " = " + userRentedBooksTable.getTableCellWithID(requestBookList.getParameter("bookid")));

		loanDao.delete(expectedUid,expectedUid);
		
		
		logger.info("Exited testRentBook");
	
	}
	
}
