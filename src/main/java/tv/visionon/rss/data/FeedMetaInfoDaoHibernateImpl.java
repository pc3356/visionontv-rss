/**
 * 
 */
package tv.visionon.rss.data;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;

/**
 * @author administrator
 *
 */
public class FeedMetaInfoDaoHibernateImpl extends HibernateDaoSupport implements FeedMetaInfoDao
{

	public FeedMetaInfoDaoHibernateImpl(HibernateTemplate hibernateTemplate) {
		setHibernateTemplate(hibernateTemplate);
	}
	
	/* (non-Javadoc)
	 * @see FeedMetaInfoDao#loadFeedByShortName(java.lang.String)
	 */
	@Override
	public FeedMetaInfo loadFeedByShortName(String shortName) throws DataAccessException
	{
		return (FeedMetaInfo)getHibernateTemplate().find("from feed where short_name = :short_name", shortName);
	}

	/* (non-Javadoc)
	 * @see FeedMetaInfoDao#saveFeed(FeedMetaInfo)
	 */
	@Override
	public void saveFeed(FeedMetaInfo feed) throws DataAccessException
	{
		getHibernateTemplate().saveOrUpdate(feed);
		saveEntries(feed);
	}
	
	private void saveEntries(FeedMetaInfo feed) {
		if(feed.getEntries() != null) {
			for(EntryMetaInfo entry : feed.getEntries()) {
				saveEntry(entry);
			}
		}
	}
	
	private void saveEntry(EntryMetaInfo entry) {
		getHibernateTemplate().saveOrUpdate(entry);
	}

	@Override
	public List<FeedMetaInfo> loadActiveFeeds() throws DataAccessException {
		return getHibernateTemplate().find("from FeedMetaInfo where active = true");
	}
}
