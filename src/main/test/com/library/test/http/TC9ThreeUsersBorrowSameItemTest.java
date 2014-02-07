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
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

public class TC9ThreeUsersBorrowSameItemTest extends TestCase {
	private static Logger logger = Logger.getLogger(RentBookServletTest.class);
	String uId1;
	String uId2;
	String uId3;
	
	String isbn;
	String bookID;
	
	String loanID1;
	String loanID2;
	String loanID3;
	
	
	
	String lId1;
	String lId2;
	String lId3;
	
	UserService userService;
	BookService bookService;
	LoanService loanService;
	
	LoanDao loanDao;
	UserDao userDao;
	BookDao bookDao;
	
	
	public TC9ThreeUsersBorrowSameItemTest(String s) {
		super(s);
	}

	public void setUp() throws Exception {
		logger.info("Entered setUp");
		//generating random users ID
		UUID uuid1 = UUID.randomUUID();
		UUID uuid2 = UUID.randomUUID();
		UUID uuid3 = UUID.randomUUID();
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		
		User user1 = new User("fName" + uuid1, "lName" + uuid1, "uName" + uuid1,
				"pWord" + uuid1, Role.STUDENT);
		User user2 = new User("fName" + uuid2, "lName" + uuid2, "uName" + uuid2,
				"pWord" + uuid2, Role.STUDENT); 
		User user3 = new User("fName" + uuid3, "lName" + uuid3, "uName" + uuid3,
				"pWord" + uuid3, Role.STUDENT);
		
		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);

		this.isbn = "isbn" + uuid1;
		Book book = new Book("bookname" + uuid1, isbn, 10);
		this.bookID = bookDao.saveOrUpdate(book);
		
		uId1 = user1.getUserId();
		uId2 = user2.getUserId();
		uId3 = user3.getUserId();
		
		//create loan for user 1
				loanDao = new LoanDao(session);
				loanService = new LoanService(loanDao);
				loanService.addLoan(this.uId1, this.bookID);
				loanID1 = this.loanDao.getLoanByUserIdBookId(uId1, bookID).getLoanId(); 
				bookService.decreaseCopies(this.bookID);

		//create loan for user 2
						loanDao = new LoanDao(session);
						loanService = new LoanService(loanDao);
						loanService.addLoan(uId1, this.bookID);
						loanID1 = this.loanDao.getLoanByUserIdBookId(uId2, bookID).getLoanId(); 
						bookService.decreaseCopies(this.bookID);

						//create loan user 3
								loanDao = new LoanDao(session);
								loanService = new LoanService(loanDao);
								loanService.addLoan(this.uId3, this.bookID);
								loanID3 = this.loanDao.getLoanByUserIdBookId(uId3, bookID).getLoanId(); 
								bookService.decreaseCopies(this.bookID);
		
		
		
		
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
/*
	public void testRentBookListBooks() throws Exception {
		logger.info("Entering testRentBookListBooks");
		WebConversation conversation = new WebConversation();
		// WebRequest request = new
		// GetMethodWebRequest(Constant.USERBOOKS_GET_URL);
		// WebResponse response = conversation.getResponse(request);

		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);

		WebRequest requestBookList = new GetMethodWebRequest(
				Constant.USERBOOKS_GET_URL);
		WebResponse responseBookList = conversation
				.getResponse(requestBookList);
		WebTable userBookListTable = responseBookList
				.getTableWithID("userBookListTable");
		// TableCell tableCell = userBookListTable.getTableCellWithID("isbn" +
		// "");
		/*
		 * Testing number of books for user panel when clicked "RentBook" FIXME
		 * : change '6' to the actualy number of books you have in your database
		 

		requestBookList.setParameter("currentUser", "20");

		assertEquals("coloumn count", bookDao.getAll().size(),
				userBookListTable.getRowCount() - 1);
		logger.info("Getting Row Count" + " = "
				+ userBookListTable.getRowCount());
		logger.info("Exited testRentBook");

	}*/

	public void testRentBookResult() throws Exception {
		logger.info("Entered testTC3AddTitle");
		WebConversation conversation = new WebConversation();
		// WebRequest request = new GetMethodWebRequest(Constant.RENT_BOOK_URL);
		// WebResponse response = conversation.getResponse(request);

		// request.setParameter("currentUser", "20");
		;

		WebRequest requestBookList = new GetMethodWebRequest(
				Constant.RENT_BOOK_URL);
		// user ID to rent
		String expectedUid = uId1;
		// book ID to rent
		String expectedBid = bookID;

		
		
		
		
		
		requestBookList.setParameter("auser", expectedUid);
		requestBookList.setParameter("bookid", expectedBid);
		WebResponse responseBookList = conversation
				.getResponse(requestBookList);
		WebTable userRentedBooksTable = responseBookList
				.getTableWithID("rentedBooks");

		// TableCell tableCell = userBookListTable.getTableCellWithID("isbn" +
		// "");
		/*
		 * Testing whether the book was added and shown on the user panel FIXME
		 * :
		 */
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);
		Loan ln = new Loan(requestBookList.getParameter("auser"),
				requestBookList.getParameter("bookid"));
		ls.addLoan(requestBookList.getParameter("auser"),
				requestBookList.getParameter("bookid"));

		int aRow = loanDao.getAll().size();
		// TableCell tc = userRentedBooksTable.getAttribute(name)

		System.out.println(aRow);

		
		//first Assertion
		assertEquals("Testing U1 burrowed", expectedBid,
				responseBookList.getTableWithID(bookID));
				//responseBookList.getTableWithID("rentedBooks")
				//.getCellAsText(1,1));
						
		//second Assertion
		
		
		expectedUid = uId2;
		// book ID to rent
		expectedBid = bookID;
		
		assertEquals("Testing U2 Borrowed", expectedBid,
				responseBookList.getTableWithID(bookID));
				//responseBookList.getTableWithID("rentedBooks")
				//.getCellAsText(1,1));
		
		//third Assertion
		
		
		expectedUid = uId3;
		// book ID to rent
		expectedBid = bookID;
		
		assertEquals("Testing U3 burrowed", expectedBid,
				responseBookList.getTableWithID(bookID));		
		
						//.getID());
		logger.info("Getting Row Count"
				+ " = "
				+ userRentedBooksTable.getTableCellWithID(requestBookList
						.getParameter("bookid")));

		loanDao.delete(expectedUid, expectedUid);

		logger.info("Exited testRentBook");

	}

}
