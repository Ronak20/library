package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.model.Book;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;


public class TC4  extends TestCase{
	private static Logger logger = Logger.getLogger(TC3.class);
	public String MyBook1Name = "Mybook1" + System.currentTimeMillis();
	public String MyBook2Name = "Mybook2" + System.currentTimeMillis();
	public int isbn1 = (int) System.currentTimeMillis();
	public int isbn2 = (int) System.currentTimeMillis();
	
	public TC4(String s) {
		super(s);
	}

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp for TC4 AddTwoTitle");
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
		logger.info("Exited setUp for TC4 AddTwoTitle");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("Entered teadDown of TC4 AddTwoTitle");
		//delete the book created 
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		logger.info("trying to delete the books with ISBN: "+isbn1+","+isbn2+"");
		Book book1 = bookDao.getBookByName(MyBook1Name);
		Book book2 = bookDao.getBookByName(MyBook2Name);
		try {
				bookDao.deleteBook(book1);
				bookDao.deleteBook(book2);
				
			} catch (Exception GenericException) {
				
				logger.error(GenericException.getMessage(), GenericException);
			}
		
		session.close();
		logger.info("Exited teadDown of TC4 AddTwoTitle");
	}

	public void testTC4AddTwoTitle() throws Exception {
	logger.info("Entered TC4 AddTwoTitle");
	WebConversation conversation = new WebConversation();
	WebRequest request = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
	WebResponse response = conversation.getResponse(request);
	WebForm addBookForm = response.getFormWithID("addBookForm");
	logger.debug("Add Book Form : \n" + response.getText());
	addBookForm.setParameter("bookname",
			MyBook1Name);
	addBookForm.setParameter("copies",
			"" + (int) (System.currentTimeMillis()));
	
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
			MyBook2Name);
	addBookForm2.setParameter("copies",
			"" + (int) (System.currentTimeMillis()));
	
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

	logger.info("Exited TC4 AddTwoTitle");
}

}
