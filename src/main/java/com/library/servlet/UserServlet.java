package com.library.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;


import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public UserServlet() {
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Get received");
		//response.sendRedirect("jsp/createUser.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    System.out.println("Post received");
		String userName = request.getParameter("username");
	    String firstName = request.getParameter("firstname");
	    String lastName = request.getParameter("lastname");
	    String password = request.getParameter("password");
	    String role = request.getParameter("role");
	    
	    User user = new User(firstName,lastName,userName,password,Role.parse(role));
	    
	    Session session = HibernateUtil.getSessionFactory().openSession();
	    UserDao userDao = new UserDao(session);
	    UserService userService = new UserService(userDao);
	    userService.saveOrUpdate(user);
	    session.close();
	    System.out.println("User added");
	}

}
