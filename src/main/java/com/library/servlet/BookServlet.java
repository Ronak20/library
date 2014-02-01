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
import com.library.dao.BookDao;
import com.library.model.Book;
import com.library.service.BookService;

/**
 * Servlet implementation class BookServlet
 */
public class BookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(BookServlet.class);

	/**
	 * Default constructor.
	 */
	public BookServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		BookService bookService = new BookService(bookDao);
		List<Book> bookList = bookService.getAll();
		logger.debug("bookList : "+bookList);
		request.setAttribute("bookList", bookList);
		logger.debug("Redirected to /jsp/bookList.jsp");
		session.close();
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/bookList.jsp");
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Post received");
		String copies = request.getParameter("copies");
		String isbn = request.getParameter("isbn");
		String bookname = request.getParameter("bookname");

		Book book = new Book(bookname, isbn, Integer.parseInt(copies));
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		BookService bookService = new BookService(bookDao);
		bookService.saveOrUpdate(book);
		logger.debug("Book added");
		
		List<Book> bookList = bookService.getAll();
		logger.debug("bookList : "+bookList);
		request.setAttribute("bookList", bookList);
		logger.debug("Redirected to /jsp/bookList.jsp");
		
		session.close();
		
		RequestDispatcher rd = request.getRequestDispatcher("/jsp/bookList.jsp");
		rd.forward(request, response);
	}

}
