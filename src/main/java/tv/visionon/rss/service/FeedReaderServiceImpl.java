/**
 * 
 */
package tv.visionon.rss.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import tv.visionon.rss.data.FeedDao;
import tv.visionon.rss.domain.SyndFeedTO;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author administrator
 *
 */
public class FeedReaderServiceImpl
{
	private final FeedDao feedDao;
	
	public FeedReaderServiceImpl(final FeedDao feedDao)
	{
		this.feedDao = feedDao;
	}
	
	public List<SyndFeed> loadFeeds(List<URL> feedSources) throws IOException, FeedException
	{
		List<SyndFeed> feeds = new ArrayList<SyndFeed>();
		if(feedSources != null)
		{
			for(URL feedSource : feedSources)
			{
				SyndFeedInput input = new SyndFeedInput();
		        SyndFeed feed = input.build(new XmlReader(feedSource));
		        feeds.add(feed);
			}
		}
		return feeds;
	}
	
	public List<SyndFeed> loadActiveFeeds()
	{
		//return feedDao.loadActiveFeeds();
		return null;
	}
	
	public List<SyndFeed> loadInactiveFeeds()
	{
		//return feedDao.loadInactiveFeeds();
		return null;
	}
	
	public List<SyndFeed> loadFeeds()
	{
		List<SyndFeed> allFeeds = new ArrayList<SyndFeed>();
		List<SyndFeedTO> allFeedTOs = feedDao.loadAllFeeds();
		for(SyndFeedTO sfto : allFeedTOs)
		{
			allFeeds.add(sfto.getSyndFeed());
		}
		return allFeeds;
	}
	
	public void addFeed(SyndFeed feed)
	{
		//feedDao.saveFeed(feed);
	}
	
	public void removeFeed(SyndFeed feed)
	{
		//feedDao.delete(feed);
	}
}
