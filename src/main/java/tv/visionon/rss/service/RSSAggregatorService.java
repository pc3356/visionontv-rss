package tv.visionon.rss.service;

import java.util.Map;

import tv.visionon.rss.domain.FeedMetaInfo;
import com.sun.syndication.feed.synd.SyndEntry;

public interface RSSAggregatorService
{

	SyndEntry findLatestEntryForFeed(String shortName) throws Exception;

	void updateFeed(String shortName) throws Exception;

	void addFeed(FeedMetaInfo feed) throws Exception;
	
	void doInitialPopulate(Map<String, FeedMetaInfo> sources) throws Exception;
	
	void checkActiveFeedsForUpdates() throws Exception;
	
	void checkForUpdates(FeedMetaInfo feedMetaInfo) throws Exception;
	
}