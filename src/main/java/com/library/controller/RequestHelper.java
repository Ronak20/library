package com.library.controller;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;


/**
 * This helper class actually processes user requests types and return related command.
 * 
 * @author sultan
 *
 */
public class RequestHelper {
	
	private HttpServletRequest _request;
	private Command _command = null;

	/**
	 * Constructs a RequestHelper object by a specified request.
	 * @param request
	 */
	public RequestHelper(HttpServletRequest request) {
		this._request = request;
	}
	
	/**
	 * Finds a request type and sets a command.
	 */
	public void processRequest() {
		String url = _request.getServletPath().substring(1);
		_request.setAttribute("selectedScreen", url);

		HashMap paraMap = (HashMap) _request.getParameterMap();
		Set entries = paraMap.entrySet();
		Iterator it = paraMap.entrySet().iterator();
		while (it.hasNext()) {
			// Get parameter
			Entry entry = (Entry) it.next();
			String key = (String) entry.getKey();
			String[] value = (String[]) entry.getValue();
			
			// Get a command based on the type of request.
			_command = CommandFactory.getCommand(key);
		}
		
	}
	
	/**
	 * Returns a command.
	 * @return Command
	 */
	public Command getCommand() {
		return _command;
	}

}
