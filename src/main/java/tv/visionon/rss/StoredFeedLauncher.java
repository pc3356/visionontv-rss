/**
 * 
 */
package tv.visionon.rss;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.orm.hibernate3.SessionFactoryUtils;
import org.springframework.orm.hibernate3.SessionHolder;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;
import tv.visionon.rss.service.StoredFeedService;
import tv.visionon.rss.util.FeedUtils;

/**
 * @author administrator
 *
 */
public class StoredFeedLauncher
{
	private final static Logger logger = Logger.getLogger(StoredFeedLauncher.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starting up... " + new Date());
		logger.info("Starting up...");
		ApplicationContext context = new ClassPathXmlApplicationContext("rssAggregatorContext.xml");
		SessionFactory sessionFactory = context.getBean(SessionFactory.class);
		
		Session session = SessionFactoryUtils.getSession(sessionFactory, true);
		TransactionSynchronizationManager.bindResource(sessionFactory, new SessionHolder(session));
		
		StoredFeedService storedFeedService = context.getBean("storedFeedService", StoredFeedService.class);
		List<FeedMetaInfo> feeds = storedFeedService.loadActiveFeeds();
		
		for(FeedMetaInfo feed : feeds) {
			System.out.println(feed.getShortName() + " : " + feed.getUri() + " : " + feed.getPublishedDate());						
			if(feed.getEntries() != null && feed.getEntries().size() > 0) {
				System.out.println("Feed has " + feed.getEntries().size() + " entries");
				EntryMetaInfo entry = FeedUtils.getLatestEntry(feed);
				System.out.println(entry.getTitle() + " : " + entry.getUri() + " : " + entry.getUpdatedDate());
			}			
		}

		TransactionSynchronizationManager.unbindResource(sessionFactory);
		SessionFactoryUtils.releaseSession(session, sessionFactory);
		
		System.out.println("Finished at " + new Date());
		logger.info("Finished");
	}

}
