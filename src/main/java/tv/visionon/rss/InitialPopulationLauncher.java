/**
 * 
 */
package tv.visionon.rss;

import java.util.Date;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import tv.visionon.rss.domain.FeedMetaInfo;
import tv.visionon.rss.service.RSSAggregatorService;
import tv.visionon.rss.util.FeedUtils;

/**
 * @author administrator
 *
 */
public class InitialPopulationLauncher
{
	private final static Logger logger = Logger.getLogger(InitialPopulationLauncher.class);
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		System.out.println("Starting up... " + new Date());
		logger.info("Starting up...");
		ApplicationContext context = new ClassPathXmlApplicationContext("rssAggregatorContext.xml");		
		Map<String, FeedMetaInfo> sourceFeeds = FeedUtils.readFeedMetaInfoFromFile("feeds.properties", true);
		
		System.out.println("Initialized " + sourceFeeds.size() + " feeds");
		logger.info("Initialized " + sourceFeeds.size() + " feeds");
		
		RSSAggregatorService rssAggregatorService = context.getBean("rssAggregatorService", RSSAggregatorService.class);
		rssAggregatorService.doInitialPopulate(sourceFeeds);
		System.out.println("Finished at " + new Date());
		logger.info("Finished");
	}

}
