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
	public static final String CREATE_USER_URL = DOMAIN + PORT + APP_NAME
			+ "/jsp/createUser.jsp";
	public static final String USER_GET_URL = DOMAIN + PORT + APP_NAME
			+ "/UserList";
	public static final String USERBOOKS_GET_URL = DOMAIN + PORT + APP_NAME
			+ "/listBooks";
	public static final String RENT_BOOK_URL = DOMAIN + PORT + APP_NAME
			+ "/rentBook";
	public static final String DELETE_USER_URL = DOMAIN + PORT + APP_NAME
			+ "/DeleteUser";
	public static final String RENEW_BOOK_URL = DOMAIN + PORT + APP_NAME
			+ "/RenewBook";
	
	public static final String LOGIN_URL = DOMAIN + PORT + APP_NAME
			+ "/login";
	public static final String ADMIN_USERNAME = "admin";
	public static final String ADMIN_PASSWORD = "admin";
	public static final String STUDENT_USERNAME = "student";
	public static final String STUDENT_PASSWORD = "student";

	public static String getRenewLoanUrl(String loanid, String userid) {
		return DOMAIN + PORT + APP_NAME + "/RenewBook" + "?aLoan=" + loanid
				+ "&currentUser=" + userid;
	}
	
	public static String getPayFeeUrl(String loadid,String userid)
	{
		return DOMAIN + PORT + APP_NAME +"/payFeesServlet"+"?loanid="+loadid+"&userid"+userid;
	}
}
