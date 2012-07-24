package tv.visionon.rss;

import java.util.Date;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import tv.visionon.rss.service.RSSAggregatorService;

public class ScheduleEmulator
{
	private final static Logger logger = Logger.getLogger(ScheduleEmulator.class);

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		System.out.println("Starting up... " + new Date());
		logger.info("Starting up...");
		ApplicationContext context = new ClassPathXmlApplicationContext("rssAggregatorContext.xml");
		SessionFactory sessionFactory = context.getBean(SessionFactory.class);
		
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		
		RSSAggregatorService rssAggregatorService = context.getBean("rssAggregatorService", RSSAggregatorService.class);
		rssAggregatorService.checkActiveFeedsForUpdates();
		
		// check the feeds?

		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
		
		System.out.println("Finished at " + new Date());
		logger.info("Finished");
	}

}
