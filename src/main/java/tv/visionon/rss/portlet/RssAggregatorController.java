package tv.visionon.rss.portlet;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.web.portlet.ModelAndView;
import org.springframework.web.portlet.mvc.AbstractController;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;
import tv.visionon.rss.service.StoredFeedService;

public class RssAggregatorController extends AbstractController {

	private static final Integer DEFAULT_ENTRIES_TO_SHOW = Integer.valueOf(5);
	private Integer entriesToShow; 
	private StoredFeedService storedFeedService;
	private final static Logger LOG = Logger.getLogger(RssAggregatorController.class);
	
	public void setStoredFeedService(StoredFeedService storedFeedService) {
		this.storedFeedService = storedFeedService;
	}
	
	public Integer getEntriesToShow() {
		return entriesToShow == null ? DEFAULT_ENTRIES_TO_SHOW : entriesToShow;
	}

	public void setEntriesToShow(Integer entriesToShow) {
		this.entriesToShow = entriesToShow;
	}

	@Override
	public ModelAndView handleRenderRequestInternal(javax.portlet.RenderRequest request, javax.portlet.RenderResponse response) throws Exception {
		ModelAndView mav = new ModelAndView("rss-aggregator-view");
		
		List<FeedMetaInfo> feedsToDisplay = new ArrayList<FeedMetaInfo>();
		List<FeedMetaInfo> feeds = storedFeedService.loadActiveFeeds();
		for(FeedMetaInfo feed : feeds) {
			// clone feed
			FeedMetaInfo feedToDisplay = new FeedMetaInfo(feed);
			// set entries to be the sublist
			feedToDisplay.setEntries(extractEntriesToShow(feed.getEntries()));
			feedsToDisplay.add(feedToDisplay);
		}
		mav.addObject("feeds", feedsToDisplay);
		
		return mav;
	}
	
	private List<EntryMetaInfo> extractEntriesToShow(List<EntryMetaInfo> entries) {
		List<EntryMetaInfo> displayEntries;
		if(entries.size() <= getEntriesToShow()) {
			displayEntries = entries.subList(0, entries.size() - 1);
		} else {
			displayEntries = entries.subList(0, getEntriesToShow() - 1);
		}
		return displayEntries;
	}
}
