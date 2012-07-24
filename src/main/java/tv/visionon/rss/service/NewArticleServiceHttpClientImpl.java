/**
 * 
 */
package tv.visionon.rss.service;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.log4j.Logger;

import tv.visionon.rss.domain.EntryMetaInfo;
import tv.visionon.rss.domain.FeedMetaInfo;

/**
 * @author pjc
 *
 */
public class NewArticleServiceHttpClientImpl implements NewArticleService {

	private String assetPublisherUri;
	private final static Logger LOG = Logger.getLogger(NewArticleServiceHttpClientImpl.class);
	
	public NewArticleServiceHttpClientImpl(String assetPublisherUri) {
		this.assetPublisherUri = assetPublisherUri;
	}
	
	/* (non-Javadoc)
	 * @see NewArticleService#addArticle(FeedMetaInfo, EntryMetaInfo)
	 */
	@Override
	public void addArticle(FeedMetaInfo feed, EntryMetaInfo entry) throws NewArticleException {
		HttpClient httpClient = new DefaultHttpClient();
		HttpPost post = new HttpPost(assetPublisherUri);
		// add post contents here
		try {
			HttpResponse response = httpClient.execute(post);
			// check the response?
			LOG.info(response);
		} catch(ClientProtocolException cpe) {
			throw new NewArticleException("Wrong protocol", cpe);
		} catch(IOException ioe) {
			throw new NewArticleException(ioe);
		}
	}

}
