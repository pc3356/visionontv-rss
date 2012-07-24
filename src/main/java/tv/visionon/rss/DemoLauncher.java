/**
 * 
 */
package tv.visionon.rss;

import java.io.IOException;
import java.util.Date;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import tv.visionon.rss.domain.FeedMetaInfo;
import tv.visionon.rss.service.RSSReaderService;
import tv.visionon.rss.service.RSSReaderServiceImpl;
import tv.visionon.rss.util.FeedUtils;
import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;

/**
 * @author administrator
 *
 */
public class DemoLauncher
{
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception
	{
		DemoLauncher launcher = new DemoLauncher();
		
		Map<String, FeedMetaInfo> sources = launcher.readFeedMetaInfoFromFile("/tmp/feeds.properties", false);
		
		RSSReaderService service = new RSSReaderServiceImpl();
		System.out.println("Started reading at " + new Date());
		Map<String, SyndFeed> feeds = service.readAggregatedData(sources);
		System.out.println("Finished reading " + feeds.size() + " feeds at " + new Date());
		
		//launcher.outputEntries(feeds);
		
		for(String shortName : feeds.keySet()) {
			SyndFeed feed = feeds.get(shortName);
			System.out.println(shortName + " : " + feed.getPublishedDate());
			SyndEntry entry = launcher.getLatestEntry(feed);
			System.out.println(shortName + ": Latest entry appears to be : \n\tTitle: " + 
					entry.getTitle().trim() + "\n\tURI: " +
					entry.getUri().trim() + "\n\tUpdated: " + 
					entry.getUpdatedDate() + "\n\tLink: " +
					entry.getLink().trim());
			SyndContent description = entry.getDescription();
			System.out.println("Desc: " + description.getValue());
			List contentItems = entry.getContents();
			for(Object obj : contentItems) {
				SyndContent contentItem = (SyndContent)obj;
				System.out.println(contentItem);
			}
		}
		
	}
	
	private DemoLauncher() {
		
	}
	
	private Map<String, FeedMetaInfo> readFeedMetaInfoFromFile(String filename, boolean fromClassPath) throws IOException {
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
			sources.put(shortName, meta);			
		}
		return sources;
	}
	
	private void outputEntries(Map<String, SyndFeed> feeds) throws IOException, FeedException {
		for(String shortName : feeds.keySet()) {
			SyndFeed feed = feeds.get(shortName);
			System.out.println(shortName + " has " + feed.getEntries().size() + " entries");
			for(Object o : feed.getEntries()) {
				SyndEntry entry = (SyndEntry)o;
				System.out.println("Title : " + entry.getTitle());
				System.out.println("Uri : " + entry.getUri());
				System.out.println("Updated : " + entry.getUpdatedDate());
			}
		}
	}	
	
	private SyndEntry getLatestEntry(SyndFeed feed) {
		return FeedUtils.getLatestEntry(feed);
	}
}
