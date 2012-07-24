package tv.visionon.rss.data;

import java.util.List;

import org.springframework.dao.DataAccessException;

import tv.visionon.rss.domain.FeedMetaInfo;

public interface FeedMetaInfoDao
{
	FeedMetaInfo loadFeedByShortName(String shortName) throws DataAccessException;
	
	void saveFeed(FeedMetaInfo feed) throws DataAccessException;
	
	List<FeedMetaInfo> loadActiveFeeds() throws DataAccessException;
}
