/**
 * 
 */
package tv.visionon.rss.service;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import tv.visionon.rss.data.FeedMetaInfoDao;
import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;
import tv.visionon.rss.exception.FeedNotFoundException;
import tv.visionon.rss.util.FeedUtils;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;

/**
 * @author administrator
 *
 */
public class RSSAggregatorServiceImpl implements RSSAggregatorService
{
	private final static Logger logger = Logger.getLogger(RSSAggregatorServiceImpl.class);
	
	private FeedMetaInfoDao feedMetaInfoDao;
	private RSSReaderService rssReaderService;
	private NewArticleService newArticleService;
	
	public RSSAggregatorServiceImpl(FeedMetaInfoDao feedMetaInfoDao, RSSReaderService rssReaderService)
	{
		super();
		this.feedMetaInfoDao = feedMetaInfoDao;
		this.rssReaderService = rssReaderService;
	}
	
	/* (non-Javadoc)
	 * @see RSSAggregatorService#findLatestEntryForFeed(java.lang.String)
	 */
	public SyndEntry findLatestEntryForFeed(String shortName) throws Exception {
		
		// get feed by shortname
		FeedMetaInfo meta = feedMetaInfoDao.loadFeedByShortName(shortName);
		URL feedUrl = meta.getUrl();
		SyndFeed feed = rssReaderService.loadFeed(feedUrl);
		
		// get latest entry
		SyndEntry entry = FeedUtils.getLatestEntry(feed);
		return entry;
	}
	
	public void checkActiveFeedsForUpdates() throws Exception {
		List<FeedMetaInfo> feeds = feedMetaInfoDao.loadActiveFeeds();
		for(FeedMetaInfo feed : feeds) {
			checkForUpdates(feed);
		}
	}
	
	public void checkForUpdates(FeedMetaInfo feedMetaInfo) throws Exception {
		// get feed
		SyndFeed syndFeed = rssReaderService.loadFeed(feedMetaInfo.getUrl());
		boolean feedsChanged = compareFeeds(syndFeed, feedMetaInfo);
		boolean entriesChanged = compareEntries(syndFeed, feedMetaInfo);
		
		if(feedsChanged || entriesChanged) {
			System.out.println("Saving changes");
			addFeed(feedMetaInfo);			
			System.out.println("Changes saved");
			
			if(newArticleService != null) {
				List<EntryMetaInfo> newEntries = FeedUtils.extractAdditionalEntries(feedMetaInfo, syndFeed);
				for(EntryMetaInfo entry : newEntries) {
					createNewArticleForEntry(feedMetaInfo, entry);
				}
			}
		}
	}
	
	public void createNewArticleForEntry(FeedMetaInfo feedMetaInfo, EntryMetaInfo entry) {
		try {
			logger.info("Saving article...");
			System.out.println("Saving article: " + entry);
			newArticleService.addArticle(feedMetaInfo, entry);
		} catch(NewArticleException nea) {
			// do nothing, but log this...
			System.out.println("Problem saving!");
			nea.printStackTrace();
			logger.error("Problem transferring article to server", nea);
		}
	}
	
	@Override
	public void updateFeed(String shortName) throws Exception {
		// TODO Auto-generated method stub
	}

	/* (non-Javadoc)
	 * @see RSSAggregatorService#addFeed(FeedMetaInfo)
	 */
	public void addFeed(FeedMetaInfo feed) throws Exception {
		feedMetaInfoDao.saveFeed(feed);
	}

	@Override
	public void doInitialPopulate(Map<String, FeedMetaInfo> sources) throws Exception {
		
		Map<String, SyndFeed> syndFeeds = rssReaderService.readAggregatedData(sources);
		System.out.println("With " + sources.size() + " sources, found " + syndFeeds.size() + " feeds");
		logger.info("With " + sources.size() + " sources, found " + syndFeeds.size() + " feeds");
		for(String shortName : sources.keySet()) {
			FeedMetaInfo feedMetaInfo = sources.get(shortName);
			if(syndFeeds.containsKey(shortName)) {				
				FeedUtils.populateFeedMetaInfo(feedMetaInfo, syndFeeds.get(shortName));
				System.out.println("Populated feed for " + shortName);
				logger.info("Populated feed for " + shortName);
				
				System.out.println("Feed is now " + feedMetaInfo);
				System.out.println("Feed has " + feedMetaInfo.getEntries().size() + " entries");
				
				System.out.println("Writing to DB...");
				addFeed(feedMetaInfo);
				System.out.println("Written");
			}
		}
	}
	
	boolean compareFeeds(SyndFeed syndFeed, FeedMetaInfo feedMetaInfo) throws FeedNotFoundException {
		// check whether it appears to have been updated
		// published dates + entry counts
		if(syndFeed == null) {
			throw new FeedNotFoundException(feedMetaInfo);
		} 
		
		if(syndFeed.getPublishedDate() != null && 
				feedMetaInfo.getPublishedDate() != null &&
				syndFeed.getPublishedDate().after(feedMetaInfo.getPublishedDate())) {			
			// update it if it's newer
			FeedUtils.populateFeedMetaInfo(feedMetaInfo, syndFeed, false); // could be adjusted to do an upsert...
			return true;
		}
		return false;
	}
	
	boolean compareEntries(SyndFeed syndFeed, FeedMetaInfo feedMetaInfo) throws FeedNotFoundException {
		// check for updates to entries...
		Map<String, EntryMetaInfo> entryMetaInfoMap = FeedUtils.extractUriEntryMetaMap(feedMetaInfo.getEntries());
			
		boolean changed = false;
		// update any existing, add new, non-existent..?
		for(Object obj : syndFeed.getEntries()) {			
			SyndEntry syndEntry = (SyndEntry)obj;
			String uri = syndEntry.getUri();
			// TODO - extract method
			if(entryMetaInfoMap.containsKey(uri)) {
				// check for update
				EntryMetaInfo entryMetaInfo = entryMetaInfoMap.get(uri);
				changed = compareFields(syndEntry, entryMetaInfo);
				
			} else {
				// add it
				EntryMetaInfo newEntry = FeedUtils.populateEntryMetaInfo(feedMetaInfo, syndEntry);
				feedMetaInfo.addEntry(newEntry);
				changed = true;
			}
		}	
		return changed;
	}
	
	boolean compareFields(SyndEntry syndEntry, EntryMetaInfo entryMetaInfo) {
		// TODO - extract methods - no property editor way of doing this?
		boolean changed = false;
		if(syndEntry.getUpdatedDate() != null && 
				!syndEntry.getUpdatedDate().equals(entryMetaInfo.getUpdatedDate())) {
			entryMetaInfo.setUpdatedDate(syndEntry.getUpdatedDate());
			changed = true;
		}
		if(syndEntry.getPublishedDate() != null && 
				!syndEntry.getPublishedDate().equals(entryMetaInfo.getPublishedDate())) {
			entryMetaInfo.setPublishedDate(syndEntry.getPublishedDate());
			changed = true;
		}
		if(syndEntry.getTitle() != null && 
				!syndEntry.getTitle().trim().equals(entryMetaInfo.getTitle().trim())) {
			entryMetaInfo.setTitle(syndEntry.getTitle().trim());
			changed = true;
		}
		return changed;
	}
	
	
}
