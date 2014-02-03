package com.library.test.http;


import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.meterware.httpunit.FormControl;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import com.library.model.User;
import com.library.model.Role;
import com.library.service.UserService;
import com.library.dao.UserDao;

public class TC16  extends TestCase{
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

	public void testDeleteUser() throws Exception {
		logger.debug("Entered TC16 testDeleteUser");
		User user;
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.DELETE_USER_URL);		
		String parameterUserName = "MyUser" + System.currentTimeMillis();
		user = new User("TestFirstName","TestLastName",parameterUserName,"password",Role.STUDENT);
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 UserDao userDao = new UserDao(session);
		 UserService userService = new UserService(userDao);
		 userService.saveOrUpdate(user);
		 session.close();
		 logger.debug("User added"+ user.getUsername());
		 WebResponse response = conversation.getResponse(request);
		 logger.debug("Delete User Form : \n" + response.getText());
		WebForm deleteUserForm = response.getFormWithName("Deleteform");
		(deleteUserForm.getControlWithID(user.getUserId())).setAttribute("checked", true);
		//deleteUserForm.setCheckbox("deleteThisUser", user.getUserId(), true);
		HttpUnitOptions.setScriptingEnabled(false);
		SubmitButton deleteUserSubmitButton = deleteUserForm
				.getSubmitButton("submitbutton");
		deleteUserForm.submit(deleteUserSubmitButton);
		WebRequest requestUserList = new GetMethodWebRequest(Constant.USER_GET_URL);
		WebResponse responseUserList = conversation.getResponse(requestUserList);
		WebTable userListTable = responseUserList.getTableWithID("userListTable");
		logger.info("Looking for username" + parameterUserName + "in the existing user list");
		TableCell tableCell = userListTable.getTableCellWithID(parameterUserName);
		assertNull(tableCell);
		logger.info("Exited TC16 testDeleteUser");
	}

}
