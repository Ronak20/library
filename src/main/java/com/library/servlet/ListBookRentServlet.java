package com.library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.config.PageConstant;
import com.library.dao.BookDao;
import com.library.model.Book;
import com.library.model.User;
import com.library.service.BookService;

/**
 * Servlet implementation class ListBooks
 */
public class ListBookRentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ListBookRentServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ListBookRentServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Get received");
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		BookService bs = new BookService(bookDao);
		
		List<Book> books = bs.getAllBookWithCopies();

		request.setAttribute("bookList", books);
				
		logger.info("Redirected to " + PageConstant.USER_BOOK_LIST_URL);
		this.getServletContext()
				.getRequestDispatcher(PageConstant.USER_BOOK_LIST_URL)
				.include(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Post received");
	}

}
