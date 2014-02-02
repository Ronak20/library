package com.library.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.User;
import org.apache.log4j.Logger;

/**
 * Servlet implementation class UserListServlet
 */
public class UserListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(UserListServlet.class);
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserListServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Get recieved at UserList servlet");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		List<User> userList = userDao.getAll();
		request.setAttribute("userlist", userList);
		session.close();
		this.getServletContext().getRequestDispatcher("/jsp/UserList.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		logger.debug("Post recieved at UserList servlet");
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		List<User> userList = userDao.getAll();
		request.setAttribute("userlist", userList);
		session.close();
		this.getServletContext().getRequestDispatcher("/jsp/UserList.jsp").forward(request, response);
	}

}
