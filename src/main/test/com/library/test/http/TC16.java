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
	private static Logger logger = Logger.getLogger(TC3.class);

	public TC16(String s) {
		super(s);
	}
	
	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp for TC 16 Delete User Test");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.ROOT_URL);
		HttpUnitOptions.setScriptingEnabled(false);
		WebResponse response = conversation.getResponse(request);
		logger.debug("Login Page : \n" + response.getText());
		WebForm loginForm = response.getFormWithID("loginForm");
		loginForm.setParameter("username", Constant.ADMIN_USERNAME);
		loginForm.setParameter("password", Constant.ADMIN_PASSWORD);
		SubmitButton submitButton = loginForm.getSubmitButton("loginSubmit");
		loginForm.submit(submitButton);
		logger.info("Exited setUp for TC 16 Delete User Test");
	}

	@After
	public void tearDown() throws Exception {
	}

	public void testDeleteUser() throws Exception {
		logger.info("Entered TC16 testDeleteUser");
		User user;
		String parameterUserName = "MyUser" + System.currentTimeMillis();
		user = new User("TestFirstName","TestLastName",parameterUserName,"password",Role.STUDENT);
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 UserDao userDao = new UserDao(session);
		 UserService userService = new UserService(userDao);
		 userService.saveOrUpdate(user);
		 session.close();
		 logger.info("User added"+ user.getUsername());
		 logger.info("trying to delete userID: "+user.getUserId());
		 WebConversation conversation = new WebConversation();
		 WebRequest requestDeleteUser = new GetMethodWebRequest(
				 Constant.DELETE_USER_URL+user.getUserId());
			WebResponse responseGetUser = conversation.getResponse(requestDeleteUser);
			WebTable bookListUpdatedTable = responseGetUser.getTableWithID("userListTable");
			TableCell tableUpdatedCell = bookListUpdatedTable.getTableCellWithID(user.getUserId());
		assertNull(tableUpdatedCell);
		logger.info("Exited TC16 testDeleteUser");
	}

}
