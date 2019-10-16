/**
 * 
 */
package tv.visionon.rss.exception;

import tv.visionon.rss.domain.FeedMetaInfo;

/**
 * @author administrator
 *
 */
public class FeedNotFoundException extends Exception
{
	private FeedMetaInfo feed;
	
	public FeedNotFoundException(FeedMetaInfo feed) {
		this.feed = feed;
	}

	@Override
	public String getMessage()	{
		return "Unable to retrieve feed for '" + feed.getShortName() + "' " + 
			(feed.getTitle() == null ? "" : "(" + feed.getTitle() + ")") + 
			" from " + feed.getUri();
	}
	
	
}
