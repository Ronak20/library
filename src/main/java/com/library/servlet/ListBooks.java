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
import com.library.dao.LoanDao;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.LoanService;

/**
 * Servlet implementation class ListBooks
 */
public class ListBooks extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(ListBooks.class);
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListBooks() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);
		BookDao bookDao = new BookDao (session);
		BookService bs = new BookService (bookDao);
		List<Book> books = bs.getAllBookWithCopies();
		
		String userId = request.getParameter("currentUser");
		logger.debug("userId : "+userId);
				
		request.setAttribute("bookList", books);
		
		this.getServletContext().getRequestDispatcher(PageConstant.USER_BOOK_LIST_URL).include(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
