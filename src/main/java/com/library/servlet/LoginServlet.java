package com.library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import sun.rmi.server.Dispatcher;

import com.library.config.HibernateUtil;
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Book;
import com.library.model.Loan;
import com.library.model.User;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Get received");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		User user;
		System.out.println("Post received");
		String userName = request.getParameter("username");
	    String password = request.getParameter("password");
	    
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    ServletContext sc = this.getServletContext();
	    UserDao userDao = new UserDao(session);
	    LoanDao loanDao = new LoanDao(session);
	    BookDao bookdao = new BookDao(session);
	    
	    System.out.print(userName + " " + password);
	    if(userDao.isValid(userName, password))
	    {
	    	RequestDispatcher rd = sc.getRequestDispatcher("/jsp/userlogged.jsp");
	    	System.out.println("Logged In Successfully");
	    	user = userDao.getUserByName(userName);	    	
	    	List<Loan> loans = loanDao.getLoanByUserId(user.getUserId());
	    	//Loan loans = loanDao.getLastLoanByUserId(user.getUserId());
	    	//List Book books = null;
	    	request.setAttribute("sessionCurrentUser", user);
	    	request.setAttribute("loanList", loans);
	    	//request.setAttribute("books", books);
	    	//hSession.setAttribute("currentSessionUser", user);
	    	//sendredirect.sendRedirect("/your/new/location.jsp");
	    	//response.sendRedirect("/jsp/userlogged.jsp"); //logged-in page
	    	 //logged-in page
	    	//RequestDispatcher dis = request.getRequestDispatcher(arg0);
	    	//dis.forward(request, response);
	    	//rd.forward(request, response);
	    	this.getServletContext().getRequestDispatcher("/jsp/userlogged.jsp").include(request, response);
	    }
	    else {
	    	
	    	System.out.println("Login Failed!");
	    	
	    }
	    
	    session.close();
	    
	    
	}

}
