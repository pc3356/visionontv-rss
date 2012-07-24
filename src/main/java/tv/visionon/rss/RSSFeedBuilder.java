/**
 * 
 */
package tv.visionon.rss;

import java.io.IOException;
import java.net.URL;

import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.FeedException;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;

/**
 * @author administrator
 *
 */
public class RSSFeedBuilder
{
	public SyndFeed buildFeed(URL feedUrl) throws IOException, FeedException {
		SyndFeedInput input = new SyndFeedInput();
		return input.build(new XmlReader(feedUrl));
	}
}
