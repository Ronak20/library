package com.library.test.http;

import java.util.Arrays;
import java.util.Collection;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.library.config.Constant;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class TC24 extends TestCase{
	private static Logger logger = Logger.getLogger(TC24.class);
	
	public TC24(String s) {
		super(s);
	}

	@Parameters
	public static Collection primeNumbers() {
		return Arrays.asList(new Object[][] { { "testisbn1"
				+ (int) (System.currentTimeMillis()) } });
	}

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp");
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
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
		
	}

	@Test
	public void testTC24RemoveTitle() throws Exception {
		logger.info("Entered testTC24RemoveTitle");
		WebConversation conversation = new WebConversation();
		WebRequest requestAdd = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
		WebResponse responseAdd = conversation.getResponse(requestAdd);
		WebForm addBookForm = responseAdd.getFormWithID("addBookForm");
		addBookForm.setParameter("bookname",
				"Mybook" + System.currentTimeMillis());
		addBookForm.setParameter("copies",
				"" + (int) (System.currentTimeMillis()));
		String isbn = "testisbn" + (int) (System.currentTimeMillis());
		addBookForm.setParameter("isbn", isbn);
		/*
		 * SubmitButton addBookSubmitButton = addBookForm
		 * .getSubmitButton("addBookSubmit");
		 * addBookForm.submit(addBookSubmitButton);
		 */
		addBookForm.submit();

		logger.info("One book added");
		logger.info("getting retriving book id");

		WebRequest request3 = new GetMethodWebRequest(Constant.BOOK_GET_URL);
		WebResponse response3 = conversation.getResponse(request3);
		WebTable bookListTable = response3.getTableWithID("bookListTable");
		TableCell tableCell = bookListTable.getTableCell(
				bookListTable.getRowCount() - 1, 0);
		String bookidDelete = tableCell.getText();
		logger.debug("bookidDelete : " + bookidDelete);
		TableCell tableDeleteCell = bookListTable.getTableCell(
				bookListTable.getRowCount() - 1,
				bookListTable.getColumnCount() - 1);

		logger.debug(tableDeleteCell.getText());

		// checking whether book is deleted or not
		logger.info("checking deleted book");
		WebRequest requestGetBook = new GetMethodWebRequest(
				Constant.BOOK_DELETE_URL+bookidDelete);
		WebResponse responseGetBook = conversation.getResponse(requestGetBook);
		WebTable bookListUpdatedTable = responseGetBook
				.getTableWithID("bookListTable");
		TableCell tableUpdatedCell = bookListUpdatedTable.getTableCell(
				bookListUpdatedTable.getRowCount() - 1,
				bookListUpdatedTable.getColumnCount() - 3);
		logger.debug("Updated copies : " + tableUpdatedCell.getText());
		Assert.assertNotSame(isbn, tableUpdatedCell.getText());

		logger.info("Exited testTC24RemoveTitle");
	}
}
