package com.library.test.notused;

import java.util.UUID;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebRequest;

public class TC9ThreeUsersBorrowSameItemTest extends TestCase {
	private static Logger logger = Logger
			.getLogger(TC9ThreeUsersBorrowSameItemTest.class);
	String uId1 = "1234567";
	String uId2 = "123456789";
	String uId3 = "1234567898";

	String isbn;
	String bookID;

	String loanID1;
	String loanID2;
	String loanID3;

	// generating random users ID
	UUID uuid1 = UUID.randomUUID();
	UUID uuid2 = UUID.randomUUID();
	UUID uuid3 = UUID.randomUUID();

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

		Session session = HibernateUtil.getSessionFactory().openSession();

		User user1 = new User("fName" + uuid1, "lName" + uuid1,
				"uName" + uuid1, "pWord" + uuid1, Role.STUDENT);

		User user2 = new User("fName" + uuid2, "lName" + uuid2,
				"uName" + uuid2, "pWord" + uuid2, Role.STUDENT);
		User user3 = new User("fName" + uuid3, "lName" + uuid3,
				"uName" + uuid3, "pWord" + uuid3, Role.STUDENT);

		// add book
		bookDao = new BookDao(session);
		bookService = new BookService(bookDao);

		this.isbn = "isbn" + uuid1;
		Book book = new Book("bookname" + uuid1, isbn, 10);
		// setting number of copies
		book.setCopies(2);
		this.bookID = bookDao.saveOrUpdate(book);

		// uId1 = userDao.getUserByName("uName" + uuid1).getUserId();
		// uId2 = userDao.getUserByName("uName" + uuid2).getUserId();
		// uId3 = userDao.getUserByName("uName" + uuid3).getUserId();

		// create loan for user 1
		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		loanService.addLoan(this.uId1, this.bookID);
		loanID1 = this.loanDao.getLoanByUserIdBookId(uId1, bookID).get(0)
				.getLoanId();
		bookService.decreaseCopies(this.bookID);

		// create loan for user 2
		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		loanService.addLoan(uId2, this.bookID);
		loanID2 = this.loanDao.getLoanByUserIdBookId(uId2, bookID).get(0)
				.getLoanId();
		bookService.decreaseCopies(this.bookID);

		// create loan user 3
		loanDao = new LoanDao(session);
		loanService = new LoanService(loanDao);
		loanService.addLoan(this.uId3, this.bookID);
		loanID3 = this.loanDao.getLoanByUserIdBookId(uId3, bookID).get(0)
				.getLoanId();
		bookService.decreaseCopies(this.bookID);

		/*
		 * WebConversation conversation = new WebConversation(); WebRequest
		 * request = new GetMethodWebRequest(Constant.ROOT_URL); WebResponse
		 * response = conversation.getResponse(request);
		 * logger.debug("Login Page : \n" + response.getText()); WebForm
		 * loginForm = response.getFormWithID("loginForm");
		 * loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		 * loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		 * SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		 * loginForm.submit(submitButton); logger.info("Exited setUp");
		 */
	}

	@After
	public void tearDown() throws Exception {

		/*
		 * userDao.delete(userDao.getUserByName("uName" + uuid3));
		 * userDao.delete
		 * (userDao.getUserById((loanDao.getLoanByID(loanID2).getUserId())));
		 * userDao
		 * .delete(userDao.getUserById((loanDao.getLoanByID(loanID3).getUserId
		 * ())));
		 */

		loanDao.delete(loanDao.getLoanByID(loanID1));
		loanDao.delete(loanDao.getLoanByID(loanID2));
		loanDao.delete(loanDao.getLoanByID(loanID3));

		bookDao.deleteBook(bookDao.getBookByID(bookID));

	}

	/*
	 * public void testRentBookListBooks() throws Exception {
	 * logger.info("Entering testRentBookListBooks"); WebConversation
	 * conversation = new WebConversation(); // WebRequest request = new //
	 * GetMethodWebRequest(Constant.USERBOOKS_GET_URL); // WebResponse response
	 * = conversation.getResponse(request);
	 * 
	 * Session session = HibernateUtil.getSessionFactory().openSession();
	 * BookDao bookDao = new BookDao(session);
	 * 
	 * WebRequest requestBookList = new GetMethodWebRequest(
	 * Constant.USERBOOKS_GET_URL); WebResponse responseBookList = conversation
	 * .getResponse(requestBookList); WebTable userBookListTable =
	 * responseBookList .getTableWithID("userBookListTable"); // TableCell
	 * tableCell = userBookListTable.getTableCellWithID("isbn" + // ""); /*
	 * Testing number of books for user panel when clicked "RentBook" FIXME :
	 * change '6' to the actualy number of books you have in your database
	 * 
	 * 
	 * requestBookList.setParameter("currentUser", "20");
	 * 
	 * assertEquals("coloumn count", bookDao.getAll().size(),
	 * userBookListTable.getRowCount() - 1); logger.info("Getting Row Count" +
	 * " = " + userBookListTable.getRowCount());
	 * logger.info("Exited testRentBook");
	 * 
	 * }
	 */

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
		// WebResponse responseBookList = conversation
		// .getResponse(requestBookList);
		// WebTable userRentedBooksTable = responseBookList
		// .getTableWithID("rentedBooks");

		// TableCell tableCell = userBookListTable.getTableCellWithID("isbn" +
		// "");
		/*
		 * Testing whether the book was added and shown on the user panel FIXME
		 * :
		 */
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);

		// TableCell tc = userRentedBooksTable.getAttribute(name)

		// System.out.println(aRow);

		// first Assertion
		assertEquals("Testing U1 burrowed", expectedUid, loanDao
				.getLoanByUserIdBookId(expectedUid, expectedBid).get(0)
				.getUserId());
		// responseBookList.getTableWithID("rentedBooks")
		// .getCellAsText(1,1));

		// second Assertion

		expectedUid = uId2;
		// book ID to rent
		expectedBid = bookID;

		assertEquals("Testing U2 Borrowed", expectedUid, loanDao
				.getLoanByUserIdBookId(expectedUid, expectedBid).get(0)
				.getUserId());
		// responseBookList.getTableWithID("rentedBooks")
		// .getCellAsText(1,1));

		// third Assertion

		expectedUid = uId3;
		// book ID to rent
		expectedBid = bookID;

		assertEquals("Testing U3 burrowed", expectedUid, loanDao
				.getLoanByUserIdBookId(expectedUid, expectedBid).get(0)
				.getUserId());

		// .getID());

		loanDao.delete(expectedUid, expectedUid);

		logger.info("Exited testRentBook");

	}

}
