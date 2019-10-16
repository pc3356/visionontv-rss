/**
 * 
 */
package tv.visionon.rss.scheduling;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import tv.visionon.rss.service.RSSAggregatorService;

/**
 * Placeholder - will be executable for scheduled tasks to run aggregator
 * @author administrator
 *
 */
public class RSSAggregatorJobBean
{
	private RSSAggregatorService rssAggregatorService;
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public void setRssAggregatorService(RSSAggregatorService rssAggregatorService) {
		this.rssAggregatorService = rssAggregatorService;
	}
	
	public void run() throws Exception {
		
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		
		rssAggregatorService.checkActiveFeedsForUpdates();
		
		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
	}
}
