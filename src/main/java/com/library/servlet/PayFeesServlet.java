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
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.service.BookService;
import com.library.service.LoanService;
import com.library.service.UserService;

/**
 * Servlet implementation class PayFeesServlet
 */
public class PayFeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PayFeesServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);
		UserDao userDao = new UserDao (session);
		String userid = request.getParameter("userid");
		String loanid = request.getParameter("loanid");
		UserService us = new UserService (userDao);
		//List<Book> books = bookDao.getAll();
		
		String userId = request.getParameter("currentUser");
		System.out.println(userId);
		
		/*
		 * user service pay fine 
		 * 
		 */
		
		us.payFees(loanid);
		
		request.setAttribute("sessionCurrentUser", userDao.getUserById(userid));
		request.setAttribute("loanList", loanDao.getLoanByUserId(userid));
		
		request.getSession().getServletContext().getRequestDispatcher("/jsp/userlogged.jsp").include(request, response);
		
		
	}
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
