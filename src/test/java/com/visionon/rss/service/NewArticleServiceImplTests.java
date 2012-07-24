package com.visionon.rss.service;


import java.net.URI;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.web.client.RestTemplate;

import static org.mockito.BDDMockito.*;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;
import tv.visionon.rss.service.NewArticleServiceRestTemplateImpl;

@RunWith(MockitoJUnitRunner.class)
public class NewArticleServiceImplTests {

	NewArticleServiceRestTemplateImpl service;
	@Mock
	RestTemplate restTemplate;
	
	@Before
	public void setUp() throws Exception {
		service = new NewArticleServiceRestTemplateImpl();
		service.setRestTemplate(restTemplate);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void shouldAddNewArticle() throws Exception {
		
		String targetUrl = "http://some/url/";
		service.setTargetUrl(targetUrl);
		URI ok = new URI("http://some/other/uri/denoting/success");
		
		EntryMetaInfo entry = new EntryMetaInfo();
		entry.setId(Long.valueOf(2L));
		entry.setTitle("A new entry");
		
		FeedMetaInfo feed = new FeedMetaInfo();
		feed.setId(Long.valueOf(1));
		feed.setTitle("A test feed");
		entry.setFeed(feed);
		feed.addEntry(entry);
		
		when(restTemplate.postForLocation(targetUrl, entry)).thenReturn(ok);
		
		service.addArticle(feed, entry);
		
		verify(restTemplate).postForLocation(targetUrl, entry);
		
	}
}
