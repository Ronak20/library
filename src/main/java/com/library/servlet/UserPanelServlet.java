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
import com.library.config.LogConstant;
import com.library.config.PageConstant;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Loan;
import com.library.model.User;
import com.library.service.LoanService;
import com.library.service.UserService;

/**
 * Servlet implementation class UserPanelServlet
 */
public class UserPanelServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserPanelServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UserPanelServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(LogConstant.GET_RECEIVED);
		Session hSession = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(hSession);
		LoanService loanService = new LoanService(loanDao);

		String userId = request.getParameter("userid");

		// get loan by user id
		List<Loan> loans = loanService.getLoanByUserId(userId);
		hSession.close();
		logger.debug("loans : " + loans);

		request.setAttribute("loanList", loans);
		logger.info("Redirect to " + PageConstant.USER_PAGE);
		RequestDispatcher rDispatch = this.getServletContext()
				.getRequestDispatcher(PageConstant.USER_PAGE);
		rDispatch.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(LogConstant.POST_RECEIVED);
	}

}
