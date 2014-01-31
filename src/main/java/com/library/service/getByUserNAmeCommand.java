package com.library.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;

import com.library.controller.Command;
import com.library.dao.UserDao;
import com.library.model.User;

public class getByUserNAmeCommand implements Command{

	public void execute(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
	
		
		String userId = request.getParameter("username");
		UserDao userDao = new UserDao((Session) request.getSession());
		
		User user = null;
		
		try {
			user = userDao.getUserByName(userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		request.getSession().setAttribute("accountByUsername", user);
		
	}

}
