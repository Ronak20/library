package com.library.servlet;

import java.io.IOException;
import java.util.List;

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
import com.library.model.Loan;
import com.library.service.LoanService;

/**
 * Servlet implementation class RenewBook
 */
public class RenewBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RenewBookServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RenewBookServlet() {
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
		LoanService loanService = new LoanService(loanDao);

		String userId = request.getParameter("userid");
		String loanId = request.getParameter("aLoan");

		String isRenewed = loanService.renewLoan(loanId);

		if (isRenewed.equals("Renewed")) {
			request.setAttribute("message", isRenewed);
		} else if (isRenewed.equals("FeePending")) {
			request.setAttribute("message", isRenewed);
		} else {
			request.setAttribute("message", isRenewed);
		}

		List<Loan> loanList = loanService.getLoanByUserId(userId);
		request.setAttribute("loanList", loanList);

		logger.info(LogConstant.REDIRECT + PageConstant.USER_PANEL_SERVLET);
		this.getServletContext()
				.getRequestDispatcher(PageConstant.USER_PANEL_SERVLET)
				.forward(request, response);

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
