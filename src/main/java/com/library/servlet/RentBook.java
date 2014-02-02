package com.library.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.service.BookService;
import com.library.service.LoanService;

/**
 * Servlet implementation class RentBook
 */
public class RentBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RentBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
			FIXME : list books doesn't work 
		*/
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);
		BookDao bookDao = new BookDao (session);
		BookService bs = new BookService (bookDao);
		UserDao userDao = new UserDao(session); 
		String userId = request.getParameter("auserid");
		String bookId = request.getParameter("bookid");
		
		ls.addLoan(userId, bookId);
		bs.increaseCopies(bookId);
		
		
		
		request.setAttribute("bookList", bs.getAll());
		request.setAttribute("sessionCurrentUser", userDao.getUserById(userId));
		request.setAttribute("loanList", loanDao.getAll());
		
		request.getSession().getServletContext().getRequestDispatcher("/jsp/userlogged.jsp").include(request, response);
		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
