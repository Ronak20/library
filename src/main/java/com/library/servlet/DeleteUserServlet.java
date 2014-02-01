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
import com.library.model.User;
import com.library.service.UserService;

/**
 * Servlet implementation class DeleteUser
 */
public class DeleteUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 
		System.out.println("Get recieved at DeleteUser servlet");
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		List<User> userList = userDao.getAll();
		request.setAttribute("userlist", userList);
		session.close();
		this.getServletContext().getRequestDispatcher("/jsp/deleteuser.jsp").forward(request, response);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("Post recieved at deleteUser servelet");
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		UserService userservice = new UserService(userDao);
		LoanDao loanDao = new LoanDao(session);
		String[] userToDelete= request.getParameterValues("deleteThisUser");
		List<User> userList = userDao.getAll();
		request.setAttribute("userlist", userList);
		String notDeletedUsers=null; 
		if(userToDelete != null)
		{
		  for(int i =0; i< userToDelete.length ; i++)
		  {
			  if(!userservice.deleteUser(userToDelete[i], loanDao))
			  {
				  if(notDeletedUsers == null)
					  notDeletedUsers = userToDelete[i];
				  else
				 notDeletedUsers = notDeletedUsers +","+userToDelete[i];
			  }
		  }
			
		}
		session.close();
		if(notDeletedUsers != null)
			request.setAttribute("notDeletedUsers", notDeletedUsers);
		//this.getServletContext().getRequestDispatcher("/jsp/deleteuser.jsp").forward(request, response);
		response.sendRedirect("/LibraryManagement/jsp/DeleteUser");
		
	}

}
