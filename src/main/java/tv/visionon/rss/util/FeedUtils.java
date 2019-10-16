/**
 * 
 */
package tv.visionon.rss.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author administrator
 *
 */
public class FeedUtils
{
	private FeedUtils() {
		
	}
	
	public static SyndEntry getLatestEntry(SyndFeed feed) {
		int equalDates = 0;
		SyndEntry latest = (SyndEntry)(feed.getEntries().size() > 0 ? feed.getEntries().get(0) : null);
		for(Object o : feed.getEntries()) {
			SyndEntry entry = (SyndEntry)o;
			if(entry.getUpdatedDate().after(latest.getUpdatedDate())) {
				latest = entry;
			} else if(!latest.equals(entry) && entry.getUpdatedDate().equals(entry.getUpdatedDate())) {
				equalDates++;
			}
		}
		if(equalDates > 0) {
			//System.err.println(feed.getTitle() + " : " + equalDates + " entries have the same date!");
		}		
		return latest;
	}
	
	public static EntryMetaInfo getLatestEntry(FeedMetaInfo feed) {
		EntryMetaInfo latest = feed.getEntries() == null || feed.getEntries().size() == 0 ? null : feed.getEntries().get(0);
		if(feed.getEntries() != null) {
			for(EntryMetaInfo entry : feed.getEntries()) {
				if(entry.getUpdatedDate().after(latest.getUpdatedDate())) {
					latest = entry;
				}
			}
		}
		return latest;
	}
	
	public static Map<String, FeedMetaInfo> readFeedMetaInfoFromFile(String filename, boolean fromClassPath) throws IOException {
		Resource resource;
		if(fromClassPath) {
			resource = new ClassPathResource(filename);
		} else {
			resource = new FileSystemResource(filename);
		}
		Properties properties = new Properties();
		properties.load(resource.getInputStream());
		
		Map<String, FeedMetaInfo> sources = new LinkedHashMap<String, FeedMetaInfo>();
		
		for(Enumeration e = properties.keys(); e.hasMoreElements();) {
			String shortName = (String)e.nextElement();
			String uri = properties.getProperty(shortName);
			FeedMetaInfo meta = new FeedMetaInfo(uri);
			meta.setShortName(shortName);
			sources.put(shortName, meta);			
		}
		return sources;
	}
	
	/**
	 * 
	 * @param sourceEntries - untyped List- @see com.sun.syndication.feed.synd.SyndFeed 
	 * @return
	 */
	public static List<EntryMetaInfo> populateEntries(FeedMetaInfo feed, List sourceEntries) {
		List<EntryMetaInfo> populatedEntries = new ArrayList<EntryMetaInfo>();
		for(Object item : sourceEntries) {
			SyndEntry syndEntry = (SyndEntry)item;
			populatedEntries.add(populateEntryMetaInfo(feed, syndEntry));
		}
		return populatedEntries;
	}
	
	public static EntryMetaInfo populateEntryMetaInfo(FeedMetaInfo feed, SyndEntry syndEntry) {
		EntryMetaInfo entry = new EntryMetaInfo();
		entry.setTitle(syndEntry.getTitle().trim());
		entry.setPublishedDate(syndEntry.getPublishedDate());
		entry.setUpdatedDate(syndEntry.getUpdatedDate());
		entry.setUri(syndEntry.getUri());
		entry.setDescription(syndEntry.getDescription().getValue());
		entry.setFeed(feed);
		return entry;
	}
	
	public static void populateFeedMetaInfo(FeedMetaInfo feed, SyndFeed syndFeed) {
		populateFeedMetaInfo(feed, syndFeed, true);
	}
	
	public static void populateFeedMetaInfo(FeedMetaInfo feed, SyndFeed syndFeed, boolean cascade) {
		feed.setTitle(syndFeed.getTitle().trim());
		feed.setPublishedDate(syndFeed.getPublishedDate());
		feed.setEncoding(syndFeed.getEncoding());
		if(cascade) {
			List<EntryMetaInfo> entries = populateEntries(feed, syndFeed.getEntries());
			feed.setEntries(entries);
		}
	}
	
	/**
	 * @param entries - ought really be a Set..
	 * @return
	 */
	public static Map<String, EntryMetaInfo> extractUriEntryMetaMap(List<EntryMetaInfo> entries) {
		Map<String, EntryMetaInfo> entryMap = new HashMap<String, EntryMetaInfo>();		
		for(EntryMetaInfo entry : entries) {
			entryMap.put(entry.getUri(), entry);
		}	
		return entryMap;
	}
	
	public static Map<String, SyndEntry> extractUriSyndEntryMap(List entries) {
		Map<String, SyndEntry> entryMap = new HashMap<String, SyndEntry>();
		for(Object obj : entries) {
			SyndEntry entry = (SyndEntry)obj;
			entryMap.put(entry.getUri(), entry);
		}
		return entryMap;
	}
	
	public static List<EntryMetaInfo> extractAdditionalEntries(FeedMetaInfo feedMetaInfo, SyndFeed syndFeed) {
		List<EntryMetaInfo> newEntries = new ArrayList<EntryMetaInfo>();
		
		for(Object obj : syndFeed.getEntries()) {
			SyndEntry entry = (SyndEntry)obj;
			if(!entryListContainsUri(feedMetaInfo.getEntries(), entry.getUri())) {
				EntryMetaInfo entryMetaInfo = populateEntryMetaInfo(feedMetaInfo, entry);
				newEntries.add(entryMetaInfo);
			}
		}
		
		return newEntries;
	}
	
	public static boolean entryListContainsUri(List<EntryMetaInfo> entryList, String uri) {
		for(EntryMetaInfo entry : entryList) {
			if(entry.getUri().equals(uri)) {
				return true;
			}
		}
		return false;
	}
}
