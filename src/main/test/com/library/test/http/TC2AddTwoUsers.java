package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.config.Constant;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.junit.Test;

public class TC2AddTwoUsers extends TestCase {
	private static Logger logger = Logger.getLogger(BookServletTest.class);

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp for CreateUserTest");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		WebResponse response = conversation.getResponse(request);
		logger.debug("Login Page : \n" + response.getText());
		WebForm loginForm = response.getFormWithID("loginForm");
		loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		loginForm.submit(submitButton);
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
	}
	
	public void testCreatetwoUsers() throws Exception {
		System.out.println("Entered testTC2AddTwoUsers");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.CREATE_USER_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm createUserForm = response.getFormWithID("createUserForm");
		logger.debug("Create User Form : \n" + response.getText());
		String parameterFirstUserName = "MyFirstUser" + System.currentTimeMillis();
		createUserForm.setParameter("username",
				parameterFirstUserName);
		createUserForm.setParameter("firstname",
				"TestFirstName" );
		createUserForm.setParameter("lastname",
				"TestLastName" );
		createUserForm.setParameter("password",
				"password" );
		createUserForm.setParameter("role",
				"Student" );
		SubmitButton createUserSubmitButton = createUserForm
				.getSubmitButton("submitbutton");
		createUserForm.submit(createUserSubmitButton);
		
		WebRequest requestUserList = new GetMethodWebRequest(Constant.USER_GET_URL);
		WebResponse responseUserList = conversation.getResponse(requestUserList);
		WebTable userListTable = responseUserList.getTableWithID("userListTable");
		
		TableCell tableCell = userListTable.getTableCellWithID(parameterFirstUserName);
		String FirstUserName = tableCell.getText();
		logger.info("FirstUserName"+" = "+ FirstUserName);
		String parameterSecondUserName = "MySecondUser" + System.currentTimeMillis();
		createUserForm.setParameter("username",
				parameterSecondUserName);
		createUserForm.setParameter("firstname",
				"TestSecondName" );
		createUserForm.setParameter("lastname",
				"TestLastName" );
		createUserForm.setParameter("password",
				"password" );
		createUserForm.setParameter("role",
				"Student" );
		createUserForm.submit(createUserSubmitButton);
		responseUserList = conversation.getResponse(requestUserList);
		userListTable = responseUserList.getTableWithID("userListTable");
		tableCell = userListTable.getTableCellWithID(parameterSecondUserName);
		String SecondUserName = tableCell.getText();
		logger.info("FirstUserName"+" = "+ SecondUserName);
		assertEquals(parameterFirstUserName+parameterSecondUserName,FirstUserName+SecondUserName );
		
		logger.info("Exited testTC2AddTwoUsers");
	}


}
