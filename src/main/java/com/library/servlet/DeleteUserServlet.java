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
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.exception.service.ConstraintViolationException;
import com.library.service.UserService;

/**
 * Servlet implementation class DeleteUser
 */
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(DeleteUserServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteUserServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Get Received");

		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		LoanDao loanDao = new LoanDao(session);
		UserService userService = new UserService(userDao, loanDao);

		String userid = request.getParameter("userid");

		if (!userid.isEmpty() && userid != null) {
			try {
				userService.deleteUser(userid);
				request.setAttribute("isUserDeleted", true);
			} catch (ConstraintViolationException constraintViolationException) {
				request.setAttribute("isUserDeleted", false);
				logger.error(constraintViolationException.getMessage(), constraintViolationException);
			}
		}

		session.close();
		logger.debug("Redirected to " + PageConstant.USER_LIST_SERVLET);
		this.getServletContext()
				.getRequestDispatcher(PageConstant.USER_LIST_SERVLET)
				.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info("Post Received");
	}
}
