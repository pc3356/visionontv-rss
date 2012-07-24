package tv.visionon.rss.service;

import java.util.List;

import tv.visionon.rss.domain.FeedMetaInfo;

public interface StoredFeedService
{
	List<FeedMetaInfo> loadActiveFeeds() throws Exception;
}