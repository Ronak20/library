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
import com.library.dao.BookDao;
import com.library.dao.LoanDao;
import com.library.exception.dao.NotFoundException;
import com.library.exception.service.ConstraintViolationException;
import com.library.service.BookService;
import com.library.service.LoanService;

/**
 * Servlet implementation class RentBookServlet
 */
public class RentBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(RentBookServlet.class);

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RentBookServlet() {
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

		BookDao bookDao = new BookDao(session);
		BookService bookService = new BookService(bookDao);

		LoanDao loanDao = new LoanDao(session);
		LoanService loanService = new LoanService(loanDao);

		String userId = request.getParameter("userid");
		String bookId = request.getParameter("bookid");

		if (bookId != null && !bookId.isEmpty() && userId != null
				&& !userId.isEmpty()) {
			try {
				boolean isLoanAdded = loanService.addLoan(userId, bookId);
				if (isLoanAdded) {
					bookService.decreaseCopies(bookId);
					request.setAttribute("loanList",
							loanService.getLoanByUserId(userId));
					session.close();
					logger.info(LogConstant.REDIRECT + PageConstant.USER_PAGE);
					this.getServletContext()
							.getRequestDispatcher(PageConstant.USER_PAGE)
							.forward(request, response);
				} else {
					session.close();
					logger.info(LogConstant.REDIRECT
							+ PageConstant.USER_BOOK_RENT_LIST_SERVLET);
					this.getServletContext()
							.getRequestDispatcher(
									PageConstant.USER_BOOK_RENT_LIST_SERVLET)
							.forward(request, response);
				}
			} catch (NotFoundException notFoundException) {
				logger.error("Book not available", notFoundException);
				request.setAttribute("isBookAvailable", false);
				session.close();
				logger.info(LogConstant.REDIRECT
						+ PageConstant.USER_BOOK_RENT_LIST_SERVLET);
				this.getServletContext()
						.getRequestDispatcher(
								PageConstant.USER_BOOK_RENT_LIST_SERVLET)
						.forward(request, response);
			} catch (ConstraintViolationException constraintViolationException) {
				request.setAttribute("hasOutstandingLoan", true);
				logger.error(constraintViolationException.getMessage(),
						constraintViolationException);
				session.close();
				logger.info(LogConstant.REDIRECT
						+ PageConstant.USER_BOOK_RENT_LIST_SERVLET);
				this.getServletContext()
						.getRequestDispatcher(
								PageConstant.USER_BOOK_RENT_LIST_SERVLET)
						.forward(request, response);
			}
		}
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
