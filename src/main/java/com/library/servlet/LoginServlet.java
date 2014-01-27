package com.library.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
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
	    HttpSession hSession = request.getSession(true);
	    UserDao userDao = new UserDao(session);
	    
	    System.out.print(userName + " " + password);
	    if(userDao.isValid(userName, password))
	    {
	    	System.out.println("Logged In Successfully");
	    	user = userDao.getUserByName(userName);
	    	//request.setAttribute("sessionCurrentUser", user);
	    	hSession.setAttribute("currentSessionUser", user);
	    	response.sendRedirect("/userlogged"); //logged-in page  	    	
	    }
	    else {
	    	
	    	System.out.println("Login Failed!");
	    	
	    }
	    
	    session.close();
	    
	    
	}

}
