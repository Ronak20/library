package com.library.test.http;

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
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class T10UserRenewItemTest extends TestCase {
	private static Logger logger = Logger.getLogger(RentBookServletTest.class);

	public T10UserRenewItemTest(String s) {
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

	
	public void testRenewBook() throws Exception {
		logger.info("Entering testRentBookListBooks");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.LOGIN_URL);
		request.setParameter("username", "sultan");
		request.setParameter("password", "sultan");
		WebResponse response = conversation.getResponse(request);
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		
		
		
		WebRequest requestBookList = new GetMethodWebRequest(Constant.RENEW_BOOK_URL);
		requestBookList.setParameter("aLoan", "16");
		requestBookList.setParameter("currentUser", "4");
		WebResponse responseBookList = conversation.getResponse(requestBookList);
		WebTable userBookListTable = responseBookList.getTableWithID("rentedBooks");
		//TableCell tableCell = userBookListTable.getTableCellWithID("isbn" + "");
		/*
		 * Testing number of books for user panel when clicked "RentBook"
		 * FIXME : change '6' to the actualy number of books you have in your database
		 */
		
		LoanDao loanDao = new LoanDao(session);
		
		
		
		//ls.addLoan("4", "1");
		String lid = "16";
		
		//int cRow = userBookListTable.getTableCellWithID("16").getRowSpan();
		
		
		System.out.println(loanDao.getLoanByID(lid).getRenewalCount());
		
		assertEquals("Rental count",loanDao.getLoanByID(lid).getRenewalCount(), userBookListTable.getCellAsText(2,2));
		logger.info("Getting Row Count" + " = " + userBookListTable.getRowCount());
		logger.info("Exited testRentBook");

		
	}

		
}
