package com.library.test.http;

import java.util.Calendar;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;

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
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class TC17 extends TestCase {

	private static Logger logger = Logger.getLogger(TC17.class);
	private String bookId = "";
	private String userId = "";
	private String loanId = "";
	private Session session;
	private UserDao userDao;
	private BookDao bookDao;
	private LoanDao loandao;
	private UserService userService;
	private BookService bookService;
	private LoanService loanService;
	
	public TC17(String s) {
		super(s);
	}

	@Before
	public void setUp() throws Exception {
		HttpUnitOptions.setScriptingEnabled(false);
		logger.info("Entered setUp for Tc17");
		// open the session
		session = HibernateUtil.getSessionFactory().openSession();
		userDao = new UserDao(session);
		bookDao = new BookDao(session);
		loandao = new LoanDao(session);
		userService = new UserService(userDao,loandao);
		bookService = new BookService(bookDao,loandao);
		loanService = new LoanService(loandao);
		logger.info("Exited setUp for TC17");
	}

	@After
	public void tearDown() throws Exception {
		// Delete the loan
		logger.info("Entered tear down for TC17");
		logger.info("trying to delete the loanID: " + loanId);
		loanService.deleteLoanByLoanID(loanId);
		// delete the book
		logger.info("trying to delete the BookID: " + bookId);
		bookService.deleteBook(bookId);
		// delete the user
		logger.info("trying to delete the UserID: " + userId);
		userService.delete(userService.getUserbyUserID(userId));
		// close the session
		session.close();
		logger.info("Exit tear down for TC17");
	}

	public void testDeleteUserWithLateFee() throws Exception {
		logger.debug("Entered TC17 testDeleteUserWithLateFee");
		User user;
		String parameterUserName = "MyUser" + System.currentTimeMillis();
		String IsbnName = "testISBN"+ System.currentTimeMillis();
		user = new User("TestFirstName", "TestLastName", parameterUserName,
				"password", Role.STUDENT);
		String parameterBookName = "MyBook" + System.currentTimeMillis();
		userService.saveOrUpdate(user);
		Book book = new Book(parameterBookName, IsbnName, 2);
		bookService.saveOrUpdate(book);
		session.refresh(book);
		logger.info("User added" + user.getUsername());
		// now create loan for this user
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MINUTE, 5);
		logger.info("Book " + book.getBookid() + " user "
				+ user.getUserId());
		
		Loan loan = new Loan(user.getUserId(), book.getBookid(), now.getTime(), 0, 10,
				false);
		loandao.saveOrUpdate(loan);
		loanId = loan.getLoanId();
		bookId = book.getBookid();
		userId = user.getUserId();
		logger.debug("Loan " + loan.getLoanId() + " created for user "
				+ user.getUserId());
		logger.info("Loan created: " + loan.getLoanId());
		logger.info("trying to delete userID: " + user.getUserId());
		WebConversation conversation = new WebConversation();
		WebRequest requestDeleteUser = new GetMethodWebRequest(
				Constant.DELETE_USER_URL + user.getUserId());
		Thread.sleep(3*60*1000);
		WebResponse responseGetUser = conversation
				.getResponse(requestDeleteUser);
		WebTable bookListUpdatedTable = responseGetUser
				.getTableWithID("userListTable");
		TableCell tableUpdatedCell = bookListUpdatedTable
				.getTableCellWithID(user.getUserId());
		assertEquals(tableUpdatedCell.getText(), user.getUserId());

		logger.info("Exited TC17 testDeleteUserWithLateFee");
	}

}
