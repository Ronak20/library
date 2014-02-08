package com.library.test.http;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.UserDao;
import com.library.model.User;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;

import org.junit.Test;

public class TC2 extends TestCase {
	private static Logger logger = Logger.getLogger(TC2.class);
	public String parameterFirstUserName = "MyFirstUser" + System.currentTimeMillis();
	public String parameterSecondUserName = "MySecondUser" + System.currentTimeMillis();

	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp for TC2");
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
		logger.info("Exited setUp for TC2");
	}

	@After
	public void tearDown() throws Exception {
		logger.info("Entered tearDown of TC2");
		//delete the user created 
		Session session = HibernateUtil.getSessionFactory().openSession();
		UserDao userDao = new UserDao(session);
		logger.info("trying to delete the user with UserName: "+parameterFirstUserName+", "+parameterSecondUserName);
		User user1 = userDao.getUserByName(parameterFirstUserName);
		User user2 = userDao.getUserByName(parameterSecondUserName);
		try {
				userDao.delete(user1);
				userDao.delete(user2);
				
			} catch (Exception GenericException) {
				
				logger.error(GenericException.getMessage(), GenericException);
			}
		
		session.close();
		logger.info("Exited teadDown of TC2");
	}
	
	public void testCreatetwoUsers() throws Exception {
		System.out.println("Entered testTC2AddTwoUsers");
		WebConversation conversation = new WebConversation();
		WebRequest request = new GetMethodWebRequest(Constant.CREATE_USER_URL);
		WebResponse response = conversation.getResponse(request);
		WebForm createUserForm = response.getFormWithID("createUserForm");
		logger.debug("Create User Form : \n" + response.getText());
		
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
