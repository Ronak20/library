package com.library.servlet;

import java.io.IOException;

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
import com.library.model.User;
import com.library.service.LoanService;

/**
 * Servlet implementation class PayFeesServlet
 */
public class PayFeesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(PayFeesServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public PayFeesServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		logger.info(LogConstant.GET_RECEIVED);
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService ls = new LoanService(loanDao);
		String loanid = request.getParameter("loanid");

		String userid = request.getParameter("userid");
		ls.payFees(loanid);

		request.setAttribute("loanList",
				loanDao.getLoanByUserId(userid));
		session.close();

		logger.info(LogConstant.REDIRECT + PageConstant.USER_PAGE);
		request.getSession().getServletContext()
				.getRequestDispatcher(PageConstant.USER_PAGE)
				.include(request, response);

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
