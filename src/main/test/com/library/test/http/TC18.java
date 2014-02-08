package com.library.test.http;

import static org.junit.Assert.*;

import java.util.Calendar;

import junit.framework.TestCase;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.library.config.Constant;
import com.library.config.HibernateUtil;
import com.library.dao.LoanDao;
import com.library.dao.UserDao;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.UserService;
import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpUnitOptions;
import com.meterware.httpunit.SubmitButton;
import com.meterware.httpunit.TableCell;
import com.meterware.httpunit.WebConversation;
import com.meterware.httpunit.WebForm;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.httpunit.WebTable;
import com.library.model.Loan;

public class TC18 extends TestCase {

	private static Logger logger = Logger.getLogger(TC3.class);
	
	public TC18(String s) {
		super(s);
	}
	
	@Before
	public void setUp() throws Exception {
		logger.info("Entered setUp for CreateUserTest");
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
		logger.info("Exited setUp");
	}

	@After
	public void tearDown() throws Exception {
	}

	public void testDeleteUserWithLoan() throws Exception {
		logger.info("Entered TC18 testDeleteUserWithLoan");
		User user;
		String parameterUserName = "MyUser" + System.currentTimeMillis();
		user = new User("TestFirstName","TestLastName",parameterUserName,"password",Role.STUDENT);
		 Session session = HibernateUtil.getSessionFactory().openSession();
		 UserDao userDao = new UserDao(session);
		 UserService userService = new UserService(userDao);
		 userService.saveOrUpdate(user);
		 
		 logger.info("User added"+ user.getUsername());
		 //now create loan for this user
		 Calendar now = Calendar.getInstance();
		 now.add(Calendar.MINUTE, 5);
		 Loan loan = new Loan(user.getUserId(),"1",now.getTime(),0,0,true);
		 LoanDao loandao = new LoanDao(session);
		 loandao.saveOrUpdate(loan);
		 logger.info("Loan "+loan.getLoanId()+" created for user "+ user.getUserId());
		 session.close();
		 WebConversation conversation = new WebConversation();
		 WebRequest requestDeleteUser = new GetMethodWebRequest(
				 Constant.DELETE_USER_URL+user.getUserId());
			WebResponse responseGetUser = conversation.getResponse(requestDeleteUser);
			WebTable bookListUpdatedTable = responseGetUser.getTableWithID("userListTable");
			TableCell tableUpdatedCell = bookListUpdatedTable.getTableCellWithID(user.getUserId());
		assertEquals(tableUpdatedCell.getText(),user.getUserId());
		logger.info("Exited TC18 testDeleteUserWithLoan");
	}

}
