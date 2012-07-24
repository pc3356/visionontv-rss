/**
 * 
 */
package tv.visionon.rss.service;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tv.visionon.rss.RSSFeedBuilder;
import tv.visionon.rss.domain.FeedMetaInfo;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

/**
 * @author administrator
 *
 */
public class RSSReaderServiceImpl implements RSSReaderService
{
	private final static Logger logger = Logger.getLogger(RSSReaderServiceImpl.class);
	
	private RSSFeedBuilder feedBuilder;
	
	public RSSReaderServiceImpl() {
		this(new RSSFeedBuilder());
	}
	
	public RSSReaderServiceImpl(RSSFeedBuilder feedBuilder) {
		this.feedBuilder = feedBuilder;
	}
	
	public Map<String, SyndFeed> readAggregatedData(Map<String, FeedMetaInfo> syndFeedMetaInfoItems) throws IOException, FeedException {
		Map<String, SyndFeed> feeds = new LinkedHashMap<String, SyndFeed>();		
		if(syndFeedMetaInfoItems != null) {
			for(String shortName : syndFeedMetaInfoItems.keySet()) {
				FeedMetaInfo syndFeedMetaInfo = syndFeedMetaInfoItems.get(shortName);
				URL feedUrl = syndFeedMetaInfo.getUrl();
				SyndFeed feed = feedBuilder.buildFeed(feedUrl);
				feeds.put(shortName, feed);
			}
		}		
		return feeds;
	}

	public SyndFeed loadFeed(URL feedUrl) throws IOException, FeedException {
		return feedBuilder.buildFeed(feedUrl);
	}
	
	/* (non-Javadoc)
	 * @see RSSReaderService#readFeed(java.net.URL)
	 */
	@Override
	public List<SyndEntry> readFeed(URL feedUrl) throws IOException, FeedException
	{
		List<SyndEntry> entries = new ArrayList<SyndEntry>();
        SyndFeed feed = feedBuilder.buildFeed(feedUrl);
        entries.addAll(feed.getEntries());
        return entries;
	}
}
