/**
 * 
 */
package tv.visionon.rss.service;

import org.springframework.web.client.RestTemplate;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;

/**
 * @author pjc
 *
 */
public class NewArticleServiceRestTemplateImpl implements NewArticleService {
	
	private RestTemplate restTemplate;
	private String targetUrl;

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}
	
	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public void addArticle(FeedMetaInfo feed, EntryMetaInfo entry) {
		// build up request		
		restTemplate.postForLocation(targetUrl, entry);
	}
}
