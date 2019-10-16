/**
 * 
 */
package tv.visionon.rss;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author administrator
 *
 */
public class Launcher
{
	private final static Logger logger = Logger.getLogger(Launcher.class);
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		logger.info("Initializing RSS aggregator");
		new ClassPathXmlApplicationContext("rssAggregatorContext.xml");
		logger.info("Loaded application context");
	}

}
