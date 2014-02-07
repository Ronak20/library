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
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Loan;
import com.library.model.User;
import com.library.service.BookService;
import com.library.service.LoanService;

/**
 * Servlet implementation class UnrentBook
 */
public class UnrentBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UnrentBook.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public UnrentBook() {
		super();
		// TODO Auto-generated constructor stub
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
		BookDao bookDao = new BookDao(session);
		BookService bookService = new BookService(bookDao);

		Loan loan = loanService.getLoanByID((String) request
				.getParameter("aLoan"));
		bookService.increaseCopies(loan.getBookId());
		loanService.deleteLoanByLoanID((String) request.getParameter("aLoan"));
		List<Loan> loans = loanService.getLoanByUserId(((User) request
				.getSession().getAttribute("user")).getUserId());
		request.setAttribute("loanList", loans);

		this.getServletContext().getRequestDispatcher(PageConstant.USER_PAGE)
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
