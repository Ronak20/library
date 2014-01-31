package com.library.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *  FrontController performs as the initial point of contact for handling a request. It
 * 		receives request from user and dispatches it to the corresponding
 *      components.
 * 
 * @author LibraryManagement, Sultan 
 * 
 */
public class FrontController extends HttpServlet {
	
	/** 
	 * Initializes the servlet.
	 */
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	/** 
	 * Destroys the servlet.
	 */
	public void destroy() {
	}
	
	/**
	 * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
	 * methods.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	public void processRequest(HttpServletRequest request, HttpServletResponse response) {
		// String page = "";

		/**
		 * ApplicationResources provides a simple API for retrieving constants
		 * and other preconfigured values
		 **/
		//ApplicationResources resource = ApplicationResources.getInstance();
		try {

			// Use a helper object to gather parameter
			// specific information.
			RequestHelper helper = new RequestHelper(request);
			helper.processRequest();
			Command cmdHelper = helper.getCommand();
			
			// Command helper perform custom operation
			cmdHelper.execute(request, response);

		} catch (Exception e) {
			/*
			LogManager.logMessage("EmployeeController:exception : "
					+ e.getMessage());
			request.setAttribute(resource.getMessageAttr(),
					"Exception occurred : " + e.getMessage());
			page = resource.getErrorPage(e);
			*/
		}
		// dispatch control to view
		dispatch("template.jsp", request, response);
	}

	/**
	 * Handles the HTTP <code>GET</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Handles the HTTP <code>POST</code> method.
	 * 
	 * @param request
	 *            servlet request
	 * @param response
	 *            servlet response
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException,
			java.io.IOException {
		processRequest(request, response);
	}

	/**
	 * Dispatches the request to the destination page.
	 * 
	 * @param destinationPage
	 * @param request
	 * @param response
	 */
	private void dispatch(String destinationPage, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			request.getRequestDispatcher(destinationPage).forward(request, response);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/** Returns a short description of the servlet */
	public String getServletInfo() {
		return "Front Controller Pattern" + " Servlet Front Strategy Example";
	}

}

