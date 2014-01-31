package com.library.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * The command interface provides a execute method for invoking database accessing service
 * and setting the result into session object.
 * 
 * @author sultan
 *
 */
public interface Command {
	
	/**
	 * Invoks database accessing service and sets the result object into session object.
	 * 
	 * @param request
	 * @param response
	 */
	public abstract void execute (HttpServletRequest request, HttpServletResponse response);
	
}
