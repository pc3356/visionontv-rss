/**
 * 
 */
package tv.visionon.rss.domain;

import java.sql.Timestamp;

import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author administrator
 *
 */
public class SyndFeedTO
{
	private final long id;
	private final SyndFeed feed;
	private boolean active;
	private long timestamp;
	
	public SyndFeedTO(Long id, SyndFeed feed)
	{
		this(id, feed, true, null);
	}
	
	public SyndFeedTO(Long id, SyndFeed feed, boolean active, Timestamp timestamp)
	{
		this.id = id;
		this.feed = feed;
		this.active = active;
		this.timestamp = timestamp != null ? timestamp.getTime() : -1L;
	}

	public boolean isActive()
	{
		return active;
	}

	public void setActive(boolean active)
	{
		this.active = active;
	}

	public Timestamp getTimestamp()
	{
		return new Timestamp(timestamp);
	}

	public void setTimestamp(Timestamp timestamp)
	{
		this.timestamp = timestamp.getTime();
	}

	public Long getId()
	{
		return Long.valueOf(id);
	}

	public SyndFeed getSyndFeed()
	{
		return feed;
	}
	
	public String getUri() 
	{
		return feed.getUri();
	}
	
	public String getLink()
	{
		return feed.getLink();
	}
}
