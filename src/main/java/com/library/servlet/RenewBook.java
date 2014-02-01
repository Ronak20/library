package com.library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Loan;
import com.library.model.User;

/**
 * Servlet implementation class RenewBook
 */
public class RenewBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RenewBook() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		

		
		Session session = HibernateUtil.getSessionFactory().openSession();
		//Loan loan = null;
		User user = null;
		
		LoanDao loanDao = new LoanDao (session);
		UserDao userDao = new UserDao(session);
		
		

		user = userDao.getUserById(request.getParameter("currentUser"));
		List<Loan> loans = loanDao.getLoanByUserId(user.getUserId());
		Loan loan = loanDao.getLoanByID(request.getParameter("aLoan"));
		
		
		//printing for tracing
		
		System.out.print(request.getParameter("currentUser"));
		
		
	
	
		//Renew Loan
		
			
		
		if (loan.getRenewalCount() <3 && loan.getIsLateFeePaid())
		{ 
			loanDao.renewLoan((String) request.getParameter("aLoan"));
			System.out.println("Renewed");
			request.setAttribute("message", "renewed");
			
		}
		else
		{
			request.setAttribute("message", "unallowed");
			System.out.println("Unallowed");
		}
		
		
		
		//printing for tracing
		
		System.out.println(loans.toString());
		
		request.setAttribute("sessionCurrentUser", user);
		request.setAttribute("loanList", loans);
		
		//printing for tracing
		System.out.println(user.toString());
		System.out.println(loans.toString());
		//request.setAttribute("loanList", request.getParameter("aLoan"));
		
		
		request.getSession().getServletContext().getRequestDispatcher("/jsp/userlogged.jsp").include(request, response);
		//response.sendRedirect("/login");

		
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
