package tv.visionon.rss.data;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.hibernate3.HibernateTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import tv.visionon.rss.domain.SyndEntryTO;
import tv.visionon.rss.domain.SyndFeedTO;

public class FeedDaoHibernateImpl extends HibernateDaoSupport implements FeedDao
{
	
	public FeedDaoHibernateImpl(HibernateTemplate hibernateTemplate)
	{
		setHibernateTemplate(hibernateTemplate);
	}

	@Override
	public List<SyndFeedTO> loadActiveFeeds() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SyndFeedTO.class, "feed");
		criteria.add(Restrictions.eq("active", true));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public List<SyndFeedTO> loadAllFeeds() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SyndFeedTO.class, "feed");
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public SyndFeedTO loadFeedByShortName(String shortName) throws DataAccessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SyndEntryTO> loadFeedEntries(Long feedId)
			throws DataAccessException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SyndFeedTO> loadInactiveFeeds() throws DataAccessException
	{
		DetachedCriteria criteria = DetachedCriteria.forClass(SyndFeedTO.class, "feed");
		criteria.add(Restrictions.eq("active", false));
		return getHibernateTemplate().findByCriteria(criteria);
	}

	@Override
	public int saveEntry(SyndEntryTO entry) throws DataAccessException
	{
		getHibernateTemplate().saveOrUpdate(entry);
		return 1;
	}

	@Override
	public boolean saveFeed(SyndFeedTO feed) throws DataAccessException
	{
		
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean saveFeed(SyndFeedTO feed, boolean cascade) throws DataAccessException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void deleteEntry(SyndEntryTO entry) throws DataAccessException
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deleteFeed(SyndFeedTO feed) throws DataAccessException
	{
		// TODO Auto-generated method stub
		
	}
}
