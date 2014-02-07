package com.library.job;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import com.library.config.HibernateUtil;
import com.library.config.LogConstant;
import com.library.dao.LoanDao;
import com.library.service.LoanService;

public class LateFeeUpdateJob implements Job {
	
	private static Logger logger = Logger.getLogger(LateFeeUpdateJob.class);
	
	public void execute(JobExecutionContext context)
			throws JobExecutionException {
		logger.info(LogConstant.ENTERED+"execute");
		
		Session session = HibernateUtil.getSessionFactory().openSession();
		LoanDao loanDao = new LoanDao(session);
		LoanService loanService = new LoanService(loanDao);
		
		loanService.updateLateFees();
		
		session.close();
		
		logger.info(LogConstant.EXITED+"execute");
	}

}