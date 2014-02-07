package com.library.test.http;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.Collection;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

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
import com.meterware.httpunit.WebLink;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

@RunWith(Parameterized.class)
public class TC5 {
	private static Logger logger = Logger.getLogger(TC5.class);
	private String isbn;
	private String bookName = "Mybook" + System.currentTimeMillis();

	public TC5(String isbn) {
		this.isbn = isbn;
	}

	@Parameters
	public static Collection primeNumbers() {
		return Arrays.asList(new Object[][] { { "testisbn1"
				+ (int) (System.currentTimeMillis()) } });
	}

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp of TC5 Add copies of a book");
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
		logger.info("Exited setUp of TC5 Add copies of a book");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("Entered tearDown of TC5 Add copies of a book");
		//delete the user created 
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		logger.info("trying to delete the book with ISBN: "+isbn+"");
		Book book = bookDao.getBookByName(bookName);
		try {
				bookDao.deleteBook(book);
				
			} catch (Exception GenericException) {
				
				logger.error(GenericException.getMessage(), GenericException);
			}
		
		session.close();
		logger.info("Exited teadDown of TC5 Add copies of a book");

	}

	@Test
	public void testTC5AddCopiesOfTwoTitle() throws Exception {
		logger.info("Entered TC5 Add copies of a book");
		WebConversation conversation = new WebConversation();
		WebRequest requestAdd = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
		WebResponse responseAdd = conversation.getResponse(requestAdd);
		WebForm addBookForm = responseAdd.getFormWithID("addBookForm");
		logger.debug("Add Book Form : \n" + responseAdd.getText());
		addBookForm.setParameter("bookname",
				bookName);
		addBookForm.setParameter("copies",
				"" + (int) (System.currentTimeMillis()));
		addBookForm.setParameter("isbn", isbn);
		/*SubmitButton addBookSubmitButton = addBookForm
				.getSubmitButton("addBookSubmit");
		addBookForm.submit(addBookSubmitButton);*/
		addBookForm.submit();

		logger.info("One book added");
		logger.info("getting retriving book id");

		WebRequest request3 = new GetMethodWebRequest(Constant.BOOK_GET_URL);
		WebResponse response3 = conversation.getResponse(request3);
		WebTable bookListTable = response3.getTableWithID("bookListTable");
		TableCell tableCell = bookListTable.getTableCell(
				bookListTable.getRowCount() - 1, 0);
		String bookidUpdate = tableCell.getText();
		logger.debug("bookidUpdate : " + bookidUpdate);
		TableCell tableUpdateCell = bookListTable.getTableCell(
				bookListTable.getRowCount() - 1,
				bookListTable.getColumnCount() - 2);
		TableCell tableBooknameCell = bookListTable.getTableCell(
				bookListTable.getRowCount() - 1,
				bookListTable.getColumnCount() - 4);
		logger.debug("Bookname : " + tableBooknameCell.getText());

		logger.debug(tableUpdateCell.getText());
		// clicking update link in table
		WebLink updateLink = tableUpdateCell.getLinks()[0];
		WebResponse updateClickResponse = updateLink.click();
		WebForm editBookForm = updateClickResponse
				.getFormWithID("editBookForm");
		editBookForm.setParameter("bookid", bookidUpdate);
		editBookForm.setParameter("bookname", tableBooknameCell.getText());
		editBookForm.setParameter("isbn", isbn);
		editBookForm.setParameter("copies", "" + 10);
		SubmitButton updateBookSubmitButton = addBookForm
				.getSubmitButton("updateBookSubmit");
		editBookForm.submit(updateBookSubmitButton);

		// checking whether book is updated or not
		logger.info("checking updated book");
		WebRequest requestGetBook = new GetMethodWebRequest(
				Constant.BOOK_GET_URL);
		WebResponse responseGetBook = conversation.getResponse(requestGetBook);
		WebTable bookListUpdatedTable = responseGetBook
				.getTableWithID("bookListTable");
		TableCell tableUpdatedCell = bookListUpdatedTable.getTableCell(
				bookListUpdatedTable.getRowCount() - 1,
				bookListUpdatedTable.getColumnCount() - 3);
		logger.debug("Updated copies : " + tableUpdatedCell.getText());
		assertEquals(10, Integer.parseInt(tableUpdatedCell.getText()));

		logger.info("Exited TC5 Add copies of a book");
	}
}
