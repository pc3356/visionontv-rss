package tv.visionon.rss.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.net.URL;
import java.util.Collections;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import tv.visionon.rss.RSSFeedBuilder;
import tv.visionon.rss.domain.FeedMetaInfo;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.feed.synd.SyndFeedImpl;
import tv.visionon.rss.service.RSSReaderService;
import tv.visionon.rss.service.RSSReaderServiceImpl;

@RunWith(MockitoJUnitRunner.class)
public class RSSReaderServiceImplTests
{
	private RSSReaderService service;
	
	@Mock
	private RSSFeedBuilder feedBuilder;
		
	@Before
	public void setUp() throws Exception {
		service = new RSSReaderServiceImpl(feedBuilder);
	}
	
	@Test
	public void shouldLoadFeed() throws Exception {
		String feedUri = "http://grassroots.mirocommunity.org/feeds/featured";
		URL feedUrl = new URL(feedUri); // won't throw an exception
		SyndFeed syndFeed = new SyndFeedImpl();
		syndFeed.setTitle("GRASSROOTS");
		syndFeed.setUri(feedUri);
		syndFeed.setLink(feedUri);
		when(feedBuilder.buildFeed(feedUrl)).thenReturn(syndFeed);
		SyndFeed actual = service.loadFeed(feedUrl);
		verify(feedBuilder).buildFeed(feedUrl);
		assertNotNull(actual);
		assertTrue(actual.getTitle().toLowerCase().contains("grassroots")); // still a little brittle...
	}

	@Test
	public void shouldAggregateSingleFeed() throws Exception {
		String shortName = "grassroots";
		String feedUri = "http://grassroots.mirocommunity.org/feeds/featured";
		URL feedUrl = new URL(feedUri); // won't throw an exception
		FeedMetaInfo meta = new FeedMetaInfo(feedUri);
		SyndFeed syndFeed = new SyndFeedImpl();
		syndFeed.setTitle("GRASSROOTS");
		syndFeed.setUri(feedUri);
		syndFeed.setLink(feedUri);
		when(feedBuilder.buildFeed(feedUrl)).thenReturn(syndFeed);
		Map<String, FeedMetaInfo> singleItemMap = Collections.singletonMap(shortName, meta);
		Map<String, SyndFeed> actual = service.readAggregatedData(singleItemMap);
		verify(feedBuilder).buildFeed(feedUrl);
		assertEquals(1, actual.size());
		assertTrue(actual.containsKey(shortName));
	}

}
