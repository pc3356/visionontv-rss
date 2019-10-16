package tv.visionon.rss.data;

import java.util.List;

import org.springframework.dao.DataAccessException;

import tv.visionon.rss.domain.SyndEntryTO;
import tv.visionon.rss.domain.SyndFeedTO;

public interface FeedDao
{

	boolean saveFeed(SyndFeedTO feed) throws DataAccessException;

	boolean saveFeed(SyndFeedTO feed, boolean cascade) throws DataAccessException;

	SyndFeedTO loadFeedByShortName(String shortName) throws DataAccessException;

	List<SyndFeedTO> loadAllFeeds() throws DataAccessException;

	List<SyndFeedTO> loadActiveFeeds() throws DataAccessException;

	List<SyndFeedTO> loadInactiveFeeds() throws DataAccessException;

	List<SyndEntryTO> loadFeedEntries(Long feedId) throws DataAccessException;

	int saveEntry(SyndEntryTO entry) throws DataAccessException;
	
	void deleteFeed(SyndFeedTO feed) throws DataAccessException;
	
	void deleteEntry(SyndEntryTO entry) throws DataAccessException;

}