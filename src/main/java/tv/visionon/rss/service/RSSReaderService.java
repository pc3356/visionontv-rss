/**
 * 
 */
package tv.visionon.rss.service;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Map;

import tv.visionon.rss.domain.FeedMetaInfo;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

/**
 * @author administrator
 *
 */
public interface RSSReaderService
{
	Map<String, SyndFeed> readAggregatedData(Map<String, FeedMetaInfo> syndFeedMetaInfoItems) throws IOException, FeedException;
	SyndFeed loadFeed(URL feedUrl) throws IOException, FeedException;	
	List<SyndEntry> readFeed(URL feedUrl) throws IOException, FeedException;
}
