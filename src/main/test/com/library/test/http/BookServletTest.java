package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;

import com.library.config.Constant;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

/**
 * TC3 and TC4
 * 
 * @author Ronak
 * 
 */
public class BookServletTest extends TestCase {
	private static Logger logger = Logger.getLogger(BookServletTest.class);

	public BookServletTest(String s) {
		super(s);
	}

	public void setUp() throws Exception {
		logger.info("Entered setUp");

		// code for login
		/*
		 * WebConversation conversation = new WebConversation(); WebRequest
		 * request = new GetMethodWebRequest(Constant.ROOT_URL); WebResponse
		 * response = conversation.getResponse(request);
		 * logger.debug("Login Page : \n" + response.getText()); WebForm
		 * loginForm = response.getFormWithID("loginForm");
		 * loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		 * loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		 * SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		 * loginForm.submit(submitButton);
		 */
		logger.info("Exited setUp");
	}

	public void tearDown() throws Exception {

	}

	public void testTC3AddTitle() throws Exception {
		logger.info("Entered testTC3AddTitle");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm addBookForm = response.getFormWithID("addBookForm");
		logger.debug("Add Book Form : \n" + response.getText());
		addBookForm.setParameter("bookname",
				"Mybook" + System.currentTimeMillis());
		addBookForm.setParameter("copies",
				"" + (int) (System.currentTimeMillis()));
		int isbn = (int) System.currentTimeMillis();
		addBookForm.setParameter("isbn", "" + isbn);
		SubmitButton addBookSubmitButton = addBookForm
				.getSubmitButton("addBookSubmit");
		addBookForm.submit(addBookSubmitButton);

		WebRequest requestBookList = new GetMethodWebRequest(
				Constant.BOOK_GET_URL);
		WebResponse responseBookList = conversation
				.getResponse(requestBookList);
		WebTable bookListTable = responseBookList
				.getTableWithID("bookListTable");
		TableCell tableCell = bookListTable.getTableCellWithID(isbn + "");
		logger.info(isbn + " = " + Integer.parseInt(tableCell.getText()));
		assertEquals(isbn, Integer.parseInt(tableCell.getText()));

		logger.info("Exited testTC3AddTitle");
	}

	public void testTC4AddTwoTitle() throws Exception {
		logger.info("Entered testTC3AddTitle");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm addBookForm = response.getFormWithID("addBookForm");
		logger.debug("Add Book Form : \n" + response.getText());
		addBookForm.setParameter("bookname",
				"Mybook" + System.currentTimeMillis());
		addBookForm.setParameter("copies",
				"" + (int) (System.currentTimeMillis()));
		int isbn1 = (int) System.currentTimeMillis();
		addBookForm.setParameter("isbn", "" + isbn1);
		SubmitButton addBookSubmitButton = addBookForm
				.getSubmitButton("addBookSubmit");
		addBookForm.submit(addBookSubmitButton);

		logger.info("One book added");

		WebRequest request2 = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
		WebResponse response2 = conversation.getResponse(request2);
		WebForm addBookForm2 = response.getFormWithID("addBookForm");
		logger.debug("Add Book Form : \n" + response2.getText());
		addBookForm2.setParameter("bookname",
				"Mybook" + System.currentTimeMillis());
		addBookForm2.setParameter("copies",
				"" + (int) (System.currentTimeMillis()));
		int isbn2 = (int) System.currentTimeMillis();
		addBookForm2.setParameter("isbn", isbn2 + "");
		SubmitButton addBookSubmitButton2 = addBookForm2
				.getSubmitButton("addBookSubmit");
		addBookForm.submit(addBookSubmitButton2);

		logger.info("checking book list");

		WebRequest request3 = new GetMethodWebRequest(Constant.BOOK_GET_URL);
		WebResponse response3 = conversation.getResponse(request3);
		WebTable bookListTable = response3.getTableWithID("bookListTable");
		TableCell tableCell1 = bookListTable.getTableCellWithID(isbn1 + "");
		TableCell tableCell2 = bookListTable.getTableCellWithID(isbn2 + "");
		logger.info(isbn1 + " = " + Integer.parseInt(tableCell1.getText()));
		logger.info(isbn2 + " = " + Integer.parseInt(tableCell2.getText()));
		assertEquals(isbn1, Integer.parseInt(tableCell1.getText()));
		assertEquals(isbn2, Integer.parseInt(tableCell2.getText()));

		logger.info("Exited testTC3AddTitle");
	}

}
