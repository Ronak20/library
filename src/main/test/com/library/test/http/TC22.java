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
import com.library.model.Loan;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class TC22 {

private static Logger logger = Logger.getLogger(TC20.class);
	
	private Session session;
	private LoanService loanService;
	private String isbn;
	private String loanID;
	private String userID;
	private String bookID;
	private BookDao bookDao;
	private UserDao userDao;
	private BookService bookService;
	private LoanDao loanDao;

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp");
		UUID uuid = UUID.randomUUID();

		// add user
		session = HibernateUtil.getSessionFactory().openSession();
		userDao = new UserDao(session);
		User user = new User("fName" + uuid, "lName" + uuid, "uName" + uuid,
				"pWord" + uuid, Role.ADMIN);
		this.userID = userDao.saveOrUpdate(user);

		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);

		this.isbn = "isbn" + uuid;
		Book book = new Book("bookname" + uuid, isbn, 10);
		this.bookID = bookDao.saveOrUpdate(book);

		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		this.loanID = loanService.addLoan(this.userID, this.bookID);
		bookService.decreaseCopies(this.bookID);
		
		loanService.renewLoan(this.userID, this.bookID);
		
		Thread.sleep(6*60*1000);
		
		loanDao.payFees(this.loanID);
		/*WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm loginForm = response.getFormWithID("loginForm");
		loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		loginForm.submit(submitButton);*/
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
		session.close();
	}
	
	@Test
	public void testTC22BorrowBookAfterPayingFine() throws InterruptedException, IOException, SAXException
	{
		logger.info("Entered testTC22BorrowBookAfterPayingFine");
		WebConversation conversation = new WebConversation();
		WebRequest requestBookList = new GetMethodWebRequest(
				Constant.RENT_BOOK_URL);
		requestBookList.setParameter("auser", userID);
		requestBookList.setParameter("bookid", bookID);
		WebResponse responseBookList = conversation
				.getResponse(requestBookList);
		
		
		Loan loan = loanDao.getLoanByUserIdBookId(userID, bookID);
		logger.debug(loan);
		Assert.assertNotNull(loan);		
		
		logger.info("Exited testTC22BorrowBookAfterPayingFine");
	}
	
}
