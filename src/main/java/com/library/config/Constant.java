package com.library.config;

public class Constant {
	private static final String DOMAIN = "http://localhost:";
	private static final String APP_NAME = "/LibraryManagement";
	private static final String PORT = "8080";
	public static final String ROOT_URL = DOMAIN + PORT + APP_NAME;
	public static final String BOOK_ADD_URL = DOMAIN + PORT + APP_NAME
			+ "/jsp/createBook.jsp";
	public static final String BOOK_GET_URL = DOMAIN + PORT + APP_NAME
			+ "/book";
	public static final String BOOK_UPDATE_URL = DOMAIN + PORT + APP_NAME
			+ "/book?bookid=";
	public static final String BOOK_DELETE_URL = DOMAIN + PORT + APP_NAME
			+ "/deleteBook?bookid=";
	public static final String ADMIN_USERNAME = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String STUDENT_USERNAME = "student";
	public static final String STUDENT_PASSWORD = "student";
}
