package com.library.test.notused;

import junit.framework.TestCase;
import junit.framework.TestSuite;

import org.apache.log4j.Logger;

import com.library.config.Constant;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;

public class LoginServletTest extends TestCase {

	private static Logger logger = Logger.getLogger(LoginServletTest.class);

	private WebForm loginForm;
	
	public static void main(String args[]) {
		junit.textui.TestRunner.run(suite());
	}

	public static TestSuite suite() {
		return new TestSuite(LoginServletTest.class);
	}

	public LoginServletTest(String s) {
		super(s);
	}

	public void setUp() throws Exception {
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		WebResponse response = conversation.getResponse(request);
		loginForm = response.getFormWithID("loginForm");
	}

	public void tearDown() throws Exception {
		
	}

	public void testStudentAuthentication() throws Exception {
		logger.info("Entered testStudentAuthentication");
		loginForm.setParameter("username", "sultan");
		loginForm.setParameter("password", "password");
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		WebResponse loginFormResponse = loginForm.submit(submitButton);
		assertEquals("Student Panel", loginFormResponse.getTitle());
		logger.info("Exited testStudentAuthentication");
	}

	public void testAdminAuthentication() throws Exception {
		logger.info("Entered testAdminAuthentication");
		loginForm.setParameter("username", "ValidUN");
		loginForm.setParameter("password", "pass");
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		WebResponse loginFormResponse = loginForm.submit(submitButton);
		assertEquals("Administrator Panel", loginFormResponse.getTitle());
		logger.info("Exited testAdminAuthentication");
	}
	
	public void testUnauthentication() throws Exception {
		logger.info("Entered testUnauthentication");
		loginForm.setParameter("username", "random");
		loginForm.setParameter("password", "random");
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		WebResponse loginFormResponse = loginForm.submit(submitButton);
		assertEquals("Login Page", loginFormResponse.getTitle());
		logger.info("Exited testUnauthentication");
	}
}
