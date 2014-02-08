package com.library.test.http;

import java.util.UUID;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Loan;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;



public class T10 extends TestCase {
	private static Logger logger = Logger.getLogger(RentBookServletTest.class);

	String uId1 = "123434yry";
	
	
	String isbn;
	String bookID;
	
	String loanID1;
	
	
	//generating random users ID
			UUID uuid1 = UUID.randomUUID();
			
	
	String lId1;
	
	
	UserService userService;
	BookService bookService;
	LoanService loanService;
	
	LoanDao loanDao;
	UserDao userDao;
	BookDao bookDao;

	private User user1;
	
	
	
	public T10(String s) {
		super(s);
	}

	public void setUp() throws Exception {
		logger.info("Entered setUp");
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		User user1 = new User("fName" + uuid1, "lName" + uuid1, "testingun",
				"pWord" + uuid1, Role.STUDENT);
		
		
		this.userDao.saveOrUpdate(user1);
		
		this.uId1 = userDao.getUserByName("testingun").getUserId(); 
		
		//userDao.saveOrUpdate(user1); 
		
		//userService.saveOrUpdate(user1);
		
		
		uId1 = userDao.getUserByName("testingun").getUserId();
		
		System.out.println("this is a user id "+uId1);
		
		
		
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);
		
		//create loan for user 1
		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		loanService.addLoan(this.uId1, this.bookID);
		loanID1 = this.loanDao.getLoanByUserIdBookId(uId1, bookID).get(0).getLoanId(); 
		bookService.decreaseCopies(this.bookID);
		
		loanService.renewLoan(loanID1);
		
		
		
		
		
		
		/*
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		WebResponse response = conversation.getResponse(request);
		logger.debug("Login Page : \n" + response.getText());
		WebForm loginForm = response.getFormWithID("loginForm");
		loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		loginForm.submit(submitButton);*/
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
		requestBookList.setParameter("aLoan", loanID1);
		requestBookList.setParameter("currentUser", loanDao.getLoanByID(loanID1).getUserId());
		WebResponse responseBookList = conversation.getResponse(requestBookList);
		WebTable userBookListTable = responseBookList.getTableWithID("rentedBooks");
		//TableCell tableCell = userBookListTable.getTableCellWithID("isbn" + "");
		/*
		 * Testing number of books for user panel when clicked "RentBook"
		 * FIXME : change '6' to the actualy number of books you have in your database
		 */
		
		LoanDao loanDao = new LoanDao(session);
		
		
		
		//ls.addLoan("4", "1");
		
		
		//int cRow = userBookListTable.getTableCellWithID("16").getRowSpan();
		
		
		System.out.println(loanDao.getLoanByID(loanID1).getRenewalCount());
		
		
		assertEquals(1, loanDao.getLoanByID(loanID1).getRenewalCount());
		
		//assertEquals("Rental count",loanDao.getLoanByID(loanID1).getRenewalCount(), userBookListTable.getCellAsText(0, column));
		
		logger.info("Getting Row Count" + " = " + userBookListTable.getRowCount());
		logger.info("Exited testRentBook");

		
	}

		
}
