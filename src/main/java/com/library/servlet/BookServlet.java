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
import com.library.config.PageConstant;
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
		logger.info("Get received");
		Session session = HibernateUtil.getSessionFactory().openSession();
		BookDao bookDao = new BookDao(session);
		BookService bookService = new BookService(bookDao);

		String bookid = request.getParameter("bookid");
		logger.info("bookid : " + bookid);

		if (bookid == null) {
			logger.debug("book list");
			List<Book> bookList = bookService.getAll();
			logger.debug("bookList : " + bookList);
			request.setAttribute("bookList", bookList);
			logger.info("Redirected to " + PageConstant.BOOK_LIST_URL);
			session.close();
			RequestDispatcher rd = this.getServletContext()
					.getRequestDispatcher(PageConstant.BOOK_LIST_URL);
			rd.forward(request, response);
		} else {
			logger.debug("book update");
			Book book = bookService.getBookByID(bookid);
			logger.debug("book : " + book);
			request.setAttribute("book", book);
			logger.debug("Redirected to " + PageConstant.BOOK_CREATE_URL);
			session.close();
			RequestDispatcher rd = this.getServletContext()
					.getRequestDispatcher(PageConstant.BOOK_CREATE_URL);
			rd.forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Post received");
		String bookid = request.getParameter("bookid");
		String copies = request.getParameter("copies");
		String isbn = request.getParameter("isbn");
		String bookname = request.getParameter("bookname");

		logger.debug("bookid : " + bookid + " copies : " + copies + " isbn : "
				+ isbn + " bookname :" + bookname);

		if (copies != null && !copies.isEmpty() && isbn != null
				&& !isbn.isEmpty() && bookname != null && !bookname.isEmpty()) {
			Book book = new Book(bookid, bookname, isbn,
					Integer.parseInt(copies));
			Session session = HibernateUtil.getSessionFactory().openSession();
			BookDao bookDao = new BookDao(session);
			BookService bookService = new BookService(bookDao);
			bookService.saveOrUpdate(book);
			logger.info("Book added " + book);

			List<Book> bookList = bookService.getAll();
			logger.debug("bookList : " + bookList);
			request.setAttribute("bookList", bookList);
			logger.debug("Redirected to " + PageConstant.BOOK_LIST_URL);

			session.close();

			RequestDispatcher rd = this.getServletContext()
					.getRequestDispatcher(PageConstant.BOOK_LIST_URL);
			rd.forward(request, response);
		}
	}

}
