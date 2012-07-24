/**
 * 
 */
package tv.visionon.rss.service;

import java.util.List;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import tv.visionon.rss.data.FeedMetaInfoDao;
import tv.visionon.rss.domain.FeedMetaInfo;

/**
 * @author pjc
 *
 */
public class StoredFeedServiceImpl implements StoredFeedService
{	
	private FeedMetaInfoDao feedMetaInfoDao;
	
	public StoredFeedServiceImpl(FeedMetaInfoDao feedMetaInfoDao) {
		this.feedMetaInfoDao = feedMetaInfoDao;
	}
	
	@Transactional(propagation = Propagation.NESTED)
	public List<FeedMetaInfo> loadActiveFeeds() {
		return feedMetaInfoDao.loadActiveFeeds();
	}
}
