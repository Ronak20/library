package com.library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
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
		List<Book> books = bookDao.getAll();
		
		String userId = request.getParameter("currentUser");
		System.out.println(userId);
		
		
		request.setAttribute("bookList", books);
		request.setAttribute("currentUser", userId);
		
		request.getSession().getServletContext().getRequestDispatcher("/jsp/userbooklist.jsp").include(request, response);
		
		
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
