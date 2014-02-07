package com.library.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.config.PageConstant;
import com.library.dao.UserDao;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.UserService;

/**
 * Servlet implementation class UserServlet
 */
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserServlet.class);

	/**
	 * Default constructor.
	 */
	public UserServlet() {

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Get received");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Post received");
		String userName = request.getParameter("username");
		String firstName = request.getParameter("firstname");
		String lastName = request.getParameter("lastname");
		String password = request.getParameter("password");
		String role = request.getParameter("role");

		logger.info("firstName : " + firstName + " lastName : " + lastName
				+ " userName : " + userName + " password :  " + password
				+ " role : " + role);
		User user = new User(firstName, lastName, userName, password,
				Role.parse(role));

		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		UserService userService = new UserService(userDao);
		userService.saveOrUpdate(user);
		session.close();
		logger.info("User added");
		this.getServletContext().getRequestDispatcher(PageConstant.USER_LIST_SERVLET)
				.forward(request, response);
	}

}
