package com.library.job;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.SimpleScheduleBuilder;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.ee.servlet.QuartzInitializerListener;
import org.quartz.impl.StdSchedulerFactory;

import com.library.config.LogConstant;

/**
 * Application Lifecycle Listener implementation class
 * TomcatQuartzQuartzInitializerListener
 * 
 */
public class TomcatQuartzInitializerListener implements
		ServletContextListener {

	private static Logger logger = Logger
			.getLogger(TomcatQuartzInitializerListener.class);
	Scheduler scheduler = null;

	/**
	 * @see QuartzInitializerListener#QuartzInitializerListener()
	 */
	public TomcatQuartzInitializerListener() {
		super();
		try {
			scheduler = new StdSchedulerFactory().getScheduler();
		} catch (SchedulerException schedulerException) {
			logger.error("Cannot start scheduler", schedulerException);
		}
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent arg0) {
		logger.info(LogConstant.ENTERED + "contextInitialized");
		
		ServletContext context = arg0.getServletContext();
		System.setProperty("rootPath", context.getRealPath("/"));
		
		JobDetail job = JobBuilder.newJob(LateFeeUpdateJob.class)
				.withIdentity("lateFeeUpdate", "payment").build();

		// Trigger the job to run on the next round minute
		Trigger trigger = TriggerBuilder
				.newTrigger()
				.withIdentity("lateFeeUpdateTrigger", "payment")
				.withSchedule(
						SimpleScheduleBuilder.simpleSchedule()
								.withIntervalInSeconds(60).repeatForever())
				.build();

		// schedule it
		try {
			scheduler.start();
			scheduler.scheduleJob(job, trigger);
		} catch (SchedulerException schedulerException) {
			logger.error("Cannot start scheduler", schedulerException);
		}

		logger.info(LogConstant.EXITED + "contextInitialized");
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		logger.info(LogConstant.ENTERED + "contextDestroyed");
		try {
			scheduler.shutdown();
		} catch (SchedulerException schedulerException) {
			logger.error("Cannot stop scheduler", schedulerException);
		}
		logger.info(LogConstant.ENTERED + "contextDestroyed");
	}

}
