/**
 * 
 */
package tv.visionon.rss.service;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;

/**
 * @author administrator
 *
 */
public interface NewArticleService {

	void addArticle(FeedMetaInfo feed, EntryMetaInfo entry) throws NewArticleException;
	
}
