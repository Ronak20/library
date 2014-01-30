package com.library.servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
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
 * Servlet implementation class UnrentBook
 */
public class UnrentBook extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UnrentBook() {
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
		System.out.println("Deleting..");
		System.out.print(request.getParameter("currentUser"));
		loanDao.deleteById((String) request.getParameter("aLoan"));
		
		user = userDao.getUserById(request.getParameter("currentUser"));
		System.out.println("received");
		
		request.setAttribute("sessionCurrentUser", user);
		
		System.out.println(user.toString());
		
		//request.setAttribute("loanList", request.getParameter("aLoan"));
		
		
		request.getRequestDispatcher("/jsp/userlogged.jsp").include(request, response);
		//response.sendRedirect("/login");
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		System.out.println(request.getParameter("aLoan"));
		/*Session session = HibernateUtil.getSessionFactory().getCurrentSession();
        Loan loan = null;
		
		
		loan.setBookId((String) request.getAttribute("aLoan"));
		LoanDao loanDao = new LoanDao (session);		
		System.out.print("Deleting..");
		loanDao.delete((Loan) request.getAttribute("aLoan"));
	
		this.getServletContext().getRequestDispatcher("/jsp/userlogged.jsp").include(request, response);
		
		*/
	}

}
