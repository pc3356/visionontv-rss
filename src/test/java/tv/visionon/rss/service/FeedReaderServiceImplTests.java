/**
 *
 */
package tv.visionon.rss.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.net.URL;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;

import tv.visionon.rss.data.FeedDao;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import tv.visionon.rss.service.FeedReaderServiceImpl;

/**
 * @author administrator
 *
 */
public class FeedReaderServiceImplTests
{
	private FeedReaderServiceImpl service;
	@Mock
	private FeedDao feedDao;

	@Before
	public void setUp() {
		service = new FeedReaderServiceImpl(feedDao);
	}

	@Test
    @Ignore
	public void shouldLoadSingleFeed() throws Exception {
		List<URL> urlList = Collections.singletonList(new URL("http://globalviews.mirocommunity.org/feeds/featured/"));
		List<SyndFeed> actual = service.loadFeeds(urlList);
		assertEquals(1, actual.size());
		SyndFeed feed = actual.get(0);
		List entries = feed.getEntries();
		assertTrue(entries.size() > 0);
		System.out.println("Feed:\nTitle: " + feed.getTitle());
		System.out.println("Link: " + feed.getLink());
		System.out.println("URI: " + feed.getUri());
		System.out.println("Type: " + feed.getFeedType());
		for(int i = 0; i < entries.size(); i++)
		{
			SyndEntry entry = (SyndEntry)entries.get(i);
			System.out.println("Title: " + entry.getTitle().trim());
			System.out.println("Link: " + entry.getLink().trim());
			System.out.println("Uri: " + entry.getUri().trim());
			System.out.println("UpdatedDate: " + entry.getUpdatedDate());
		}
	}
}
