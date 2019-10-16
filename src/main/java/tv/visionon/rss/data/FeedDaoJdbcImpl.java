/**
 * 
 */
package tv.visionon.rss.data;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.ParameterizedRowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import tv.visionon.rss.domain.SyndEntryTO;
import tv.visionon.rss.domain.SyndFeedTO;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

/**
 * @author administrator
 *
 */
public class FeedDaoJdbcImpl extends JdbcDaoSupport implements FeedDao
{
	private final static String INSERT_FEED = "INSERT INTO feed (title, link, uri, feed_type) VALUES (?, ?, ?, ?)";
	private final static String INSERT_ENTRY = "INSERT INTO entry (title, link, uri, published, updated) VALUES (?, ?, ?, ?, ?)";
	private final static String SELECT_FEED_BY_LINK = "SELECT title, link, uri, feed_type FROM feed WHERE link = ?";
	private final static String SELECT_FEED_ENTRIES = "SELECT id, title, link, uri, published, updated, feed_id, added FROM feed_entry WHERE feed_id = ?";
	private final static String SELECT_ALL_FEEDS = "SELECT title, link, uri, feed_type FROM feed";
	private final static String SELECT_ACTIVE_FEEDS = "SELECT title, link, uri, feed_type FROM feed WHERE active = true";
	private final static String SELECT_INACTIVE_FEEDS = "SELECT title, link, uri, feed_type FROM feed WHERE active = false";
	
	public FeedDaoJdbcImpl(JdbcTemplate jdbcTemplate)
	{
		setJdbcTemplate(jdbcTemplate);
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#saveFeed(com.sun.syndication.feed.synd.SyndFeed)
	 */
	public boolean saveFeed(SyndFeedTO feed) throws DataAccessException
	{
		return saveFeed(feed, true);
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#saveFeed(com.sun.syndication.feed.synd.SyndFeed, boolean)
	 */
	public boolean saveFeed(SyndFeedTO feed, boolean cascade) throws DataAccessException
	{
		/*
		int rows = getJdbcTemplate().update(
				INSERT_FEED, 
				feed.getTitle(), 
				feed.getLink(), 
				feed.getUri(), 
				feed.getFeedType()
		);
		
		if(cascade)
		{
			List entries = feed.getEntries();
			for(Object o : entries)
			{
				SyndEntry entry = (SyndEntry)o;
				saveEntry(entry);
			}
		}
		return rows == 1;
		*/
		return true;
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#loadFeedByShortName(java.lang.String)
	 */
	public SyndFeedTO loadFeedByShortName(String shortName) throws DataAccessException
	{
		//return getJdbcTemplate().queryForObject(SELECT_FEED_BY_LINK, new Object[]{link}, new SyndFeedRowMapper());
		return null;
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#loadAllFeeds()
	 */
	public List<SyndFeedTO> loadAllFeeds() throws DataAccessException
	{
		//return getJdbcTemplate().query(SELECT_ALL_FEEDS, new SyndFeedRowMapper());
		return null;
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#loadActiveFeeds()
	 */
	public List<SyndFeedTO> loadActiveFeeds() throws DataAccessException
	{
		//return getJdbcTemplate().query(SELECT_ACTIVE_FEEDS, new SyndFeedRowMapper());
		return null;
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#loadInactiveFeeds()
	 */
	public List<SyndFeedTO> loadInactiveFeeds() throws DataAccessException
	{
		//return getJdbcTemplate().query(SELECT_INACTIVE_FEEDS, new SyndFeedRowMapper());
		return null;
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#loadFeedEntries(java.lang.Long)
	 */
	public List<SyndEntryTO> loadFeedEntries(Long feedId) throws DataAccessException
	{
		//return getJdbcTemplate().query(SELECT_FEED_ENTRIES, new SyndEntryRowMapper());
		return null;
	}
	
	/* (non-Javadoc)
	 * @see FeedDao#saveEntry(com.sun.syndication.feed.synd.SyndEntry)
	 */
	public int saveEntry(SyndEntryTO entry) throws DataAccessException
	{
		int rows = getJdbcTemplate().update(
				INSERT_ENTRY, 
				entry.getTitle().trim(), 
				entry.getLink().trim(), 
				entry.getUri().trim()//, 
				//entry.getPublishedDate(),
				//entry.getUpdatedDate()
		);
		
		return rows;
	}

	@Override
	public void deleteEntry(SyndEntryTO entry) throws DataAccessException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFeed(SyndFeedTO feed) throws DataAccessException
	{
		// TODO Auto-generated method stub
		
	}
}

class SyndFeedRowMapper implements ParameterizedRowMapper<SyndFeed>
{
	@Override
	public SyndFeed mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		SyndFeed feed = new SyndFeedImpl();
		feed.setTitle(resultSet.getString("title"));
		feed.setLink(resultSet.getString("link"));
		feed.setUri(resultSet.getString("uri"));
		feed.setFeedType(resultSet.getString("feed_type"));
		return feed;
	}
}

class SyndEntryRowMapper implements ParameterizedRowMapper<SyndEntry>
{

	@Override
	public SyndEntry mapRow(ResultSet resultSet, int rowNum) throws SQLException
	{
		SyndEntry entry = new SyndEntryImpl();
		entry.setTitle(resultSet.getString("title"));
		entry.setLink(resultSet.getString("link"));
		entry.setUri(resultSet.getString("uri"));
		entry.setPublishedDate(resultSet.getDate("published"));
		entry.setUpdatedDate(resultSet.getDate("updated"));
		return entry;
	}
	
}
