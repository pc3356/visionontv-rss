package tv.visionon.rss.domain;

import java.sql.Timestamp;
import java.util.Date;

import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndEntryImpl;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;

//@Entity
//@Table(name="synd_entry")
public class SyndEntryTO
{
	//@Id
	private long id;
	private SyndEntry entry; // mapped entity - only really interested in a few fields
	private SyndFeed source; // parent - mapped by ID
	private long timestamp;
	
	public SyndEntryTO()
	{
		entry = new SyndEntryImpl();
		entry.setSource(new SyndFeedImpl());
	}
	
	public SyndEntryTO(SyndEntry entry)
	{
		this(null, entry, entry.getSource(), null);
	}
	
	public SyndEntryTO(Long id, SyndEntry entry, SyndFeed source, Timestamp timestamp)
	{
		this.id = id;
		this.entry = entry;
		this.source = source;
		this.timestamp = timestamp != null ? timestamp.getTime() : -1L;
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

	public SyndEntry getEntry()
	{
		return entry;
	}

	public SyndFeed getSource()
	{
		return source;
	}
	
	public String getTitle()
	{
		return entry != null ? entry.getTitle() : null;
	}
	
	public String getLink()
	{
		return entry != null ? entry.getLink() : null;
	}
	
	public Date getPublished()
	{
		return entry != null ? entry.getPublishedDate() : null;
	}
	
	public Date getUpdated()
	{
		return entry != null ? entry.getUpdatedDate() : null;
	}
	
	public String getUri()
	{
		return entry != null ? entry.getUri() : null;
	}
	
	private void setTitle(String title)
	{
		entry.setTitle(title);
	}
	
	private void setLink(String link)
	{
		entry.setLink(link);
	}
	
	private void setUri(String uri)
	{
		entry.setUri(uri);
	}
	
	private void setPublished(Date published)
	{
		entry.setPublishedDate(published);
	}
	
	private void setUpdated(Date updated)
	{
		entry.setUpdatedDate(updated);
	}
}
