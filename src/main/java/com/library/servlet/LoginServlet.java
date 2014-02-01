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
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Loan;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.LoanService;
import com.library.service.UserService;

/**
 * Servlet implementation class LoginServlet
 */
public class LoginServlet extends HttpServlet {

	private static Logger logger = Logger.getLogger(LoginServlet.class);

	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Get received");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Post received");

		String userName = request.getParameter("username");
		String password = request.getParameter("password");
		logger.debug("userName : " + userName + " password : " + password);

		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		LoanDao loanDao = new LoanDao(session);
		UserService userService = new UserService(userDao);
		LoanService loanService = new LoanService(loanDao);

		System.out.print(userName + " " + password);
		if (userService.isValid(userName, password)) {
			logger.debug("Logged In Successfully");
			User user = userService.getUserByName(userName);
			logger.debug("user : " + user);

			if (user.getRole().equals(Role.STUDENT)) {
				List<Loan> loans = loanService
						.getLoanByUserId(user.getUserId());
				logger.debug("loans : " + loans);
				session.close();
				request.setAttribute("sessionCurrentUser", user);
				request.setAttribute("loanList", loans);

				RequestDispatcher rDispatch = this.getServletContext()
						.getRequestDispatcher("/jsp/userlogged.jsp");
				rDispatch.forward(request, response);
			} else if (user.getRole().equals(Role.ADMIN)) {
				session.close();
				request.setAttribute("sessionCurrentUser", user);
				RequestDispatcher rDispatch = this.getServletContext()
						.getRequestDispatcher("/jsp/admincontrol.jsp");
				rDispatch.forward(request, response);
			}

		} else {
			logger.debug("Login Failed!");
			session.close();
		}
	}
}
