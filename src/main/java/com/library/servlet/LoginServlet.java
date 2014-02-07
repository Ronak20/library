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
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.exception.service.AuthenticationException;
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
		String password = request.getParameter("password");
		logger.info("userName : " + userName + " password : " + password);

		Session hSession = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(hSession);
		LoanDao loanDao = new LoanDao(hSession);
		UserService userService = new UserService(userDao);
		LoanService loanService = new LoanService(loanDao);
		User user = null;
		
		//get user using username and password
		try {
			user = userService.get(userName, password);
		} catch (AuthenticationException authenticationException) {
			hSession.close();
			logger.error("Not authenticated", authenticationException);
			request.setAttribute("errorMessage", "wrong username or password");
			RequestDispatcher rDispatch = this.getServletContext()
					.getRequestDispatcher(PageConstant.LOGIN_PAGE);
			rDispatch.forward(request, response);
		}

		if (user != null) {
			logger.info("Logged In Successfully " + " user : " + user);
			request.getSession().setAttribute("user", user);
			request.getSession().setMaxInactiveInterval(30*60);
			request.removeAttribute("errorMessage");
			if (user.getRole().equals(Role.STUDENT)) {
				
				//get loan by user id
				List<Loan> loans = loanService
						.getLoanByUserId(user.getUserId());

				logger.debug("loans : " + loans);
				hSession.close();
				request.setAttribute("loanList", loans);
				logger.info("Redirect to "+PageConstant.USER_PAGE);
				RequestDispatcher rDispatch = this.getServletContext()
						.getRequestDispatcher(PageConstant.USER_PAGE);

				/*
				 * if (!userService.checkPayment(userName) ) {
				 * request.setAttribute("paynote", "0"); }
				 */

				rDispatch.forward(request, response);
			} else if (user.getRole().equals(Role.ADMIN)) {
				hSession.close();
				logger.info("Redirect to "+PageConstant.ADMIN_PAGE);
				// request.setAttribute("sessionCurrentUser", user);
				RequestDispatcher rDispatch = this.getServletContext()
						.getRequestDispatcher(PageConstant.ADMIN_PAGE);
				rDispatch.forward(request, response);
			}

		}
	}
}
