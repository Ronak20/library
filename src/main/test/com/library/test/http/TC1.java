package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;

import com.library.config.Constant;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
public class TC1 extends TestCase {
	private static Logger logger = Logger.getLogger(BookServletTest.class);

	public TC1(String s) {
		super(s);
	}

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
	
	public void testCreateUser() throws Exception {
		System.out.println("Entered testTC1AddUser");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.CREATE_USER_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm createUserForm = response.getFormWithID("createUserForm");
		logger.debug("Create User Form : \n" + response.getText());
		String parameterUserName = "MyUser" + System.currentTimeMillis();
		createUserForm.setParameter("username",
				parameterUserName);
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
		
		TableCell tableCell = userListTable.getTableCellWithID(parameterUserName);
		logger.info("parameterUserName"+" = "+ tableCell.getText());
		assertEquals(parameterUserName, tableCell.getText());
		
		logger.info("Exited testTC1AddUser");
	}

	

}

