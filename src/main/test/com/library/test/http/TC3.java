package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.model.Book;
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
public class TC3 extends TestCase {
	private static Logger logger = Logger.getLogger(TC3.class);
	public int isbn = (int) System.currentTimeMillis();
	public String bookName = "Mybook" + System.currentTimeMillis();

	public TC3(String s) {
		super(s);
	}

	public void setUp() throws Exception {
		logger.info("Entered setUp for testTC3AddTitle");
		
		logger.info("Exited setUp for testTC3AddTitle");

	}

	public void tearDown() throws Exception {

		logger.info("Entered teadDown of TC3 testTC3AddTitle");
		// delete the user created
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		logger.info("trying to delete the book with ISBN: " + isbn + "");
		Book book = bookDao.getBookByName(bookName);
		try {
			bookDao.deleteBook(book);

		} catch (Exception GenericException) {

			logger.error(GenericException.getMessage(), GenericException);
		}

		session.close();
		logger.info("Exited teadDown of TC3 testTC3AddTitle");

	}

	public void testTC3AddTitle() throws Exception {
		logger.info("Entered testTC3AddTitle");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.BOOK_ADD_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm addBookForm = response.getFormWithID("addBookForm");
		logger.debug("Add Book Form : \n" + response.getText());
		addBookForm.setParameter("bookname", bookName);
		addBookForm.setParameter("copies",
				"" + (int) (System.currentTimeMillis()));
		addBookForm.setParameter("isbn", "" + isbn);
		logger.info("isbn is: " + isbn + "");
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
		logger.info("Looking for book with isbn: " + isbn + "");
		assertEquals(isbn, Integer.parseInt(tableCell.getText()));

		logger.info("Exited testTC3AddTitle");
	}

}
