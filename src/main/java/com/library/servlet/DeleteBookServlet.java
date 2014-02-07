package com.library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.config.LogConstant;
import com.library.config.PageConstant;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.model.Book;
import com.library.service.BookService;

/**
 * Servlet implementation class DeleteBookServlet
 */
public class DeleteBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteBookServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteBookServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Get received");

		String bookid = request.getParameter("bookid");
		logger.debug("param bookid : " + bookid);
		Book book = new Book(bookid);
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		LoanDao loanDao = new LoanDao(session);
		BookService bookService = new BookService(bookDao, loanDao);
		if (!bookService.deleteBook(book.getBookid())) {
			logger.debug("Book not deleted");
			request.setAttribute("isBookDeleted", false);
		} else {
			request.setAttribute("isBookDeleted", true);
			logger.debug("Book  deleted");
		}
		List<Book> bookList = bookService.getAll();
		logger.debug("bookList : " + bookList);
		request.setAttribute("bookList", bookList);
		session.close();

		logger.debug(LogConstant.REDIRECT + PageConstant.BOOK_LIST_URL);
		RequestDispatcher rd = request
				.getRequestDispatcher(PageConstant.BOOK_LIST_URL);
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

}
