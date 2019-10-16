/**
 * 
 */
package tv.visionon.rss.service;

import com.sun.syndication.io.SyndFeedInput;

/**
 * @author pjc
 *
 */
public class SyndFeedInputProviderImpl implements SyndFeedInputProvider {

	/* (non-Javadoc)
	 * @see SyndFeedInputProvider#getSyndFeedInput()
	 */
	@Override
	public SyndFeedInput getSyndFeedInput() {
		return new SyndFeedInput();
	}

}
