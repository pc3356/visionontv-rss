/**
 * 
 */
package tv.visionon.http;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author administrator
 *
 */
public class AddArticleServiceImpl {
	
	private DefaultHttpClient httpClient;
	private final static String userEmail = "xyz@gmail.com";
	private final static String userName = "xyz";
	private final static String password = "kjhkjhjk";
	private final static String baseUrl = "82.35.198.188";
	
	public AddArticleServiceImpl(DefaultHttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public String addArticle(Long groupId, String title, String description, 
			String content, Date displayDate, Date expirationDate, 
			Date reviewDate, boolean indexable)  throws IOException, JSONException {
        
        WebContentForm form = prepareForm(groupId, title, description, 
        		content, displayDate, expirationDate, reviewDate, indexable);
        
        HttpResponse resp = submitForm(form);
        System.out.println(resp.getStatusLine());
        
        // parse response JSON...
        JSONObject json = parseResponseAsJSON(resp);
        
        return json.getString("articleId");
	}
	
	protected HttpResponse submitForm(WebContentForm form) throws IOException {
		HttpHost targetHost = new HttpHost(baseUrl, 80, "http");
        
        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials(userName, password));

        // Create AuthCache instance
        AuthCache authCache = new BasicAuthCache();
        // Generate BASIC scheme object and add it to the local
        // auth cache
        BasicScheme basicAuth = new BasicScheme();
        authCache.put(targetHost, basicAuth);

        // Add AuthCache to the execution context
        BasicHttpContext ctx = new BasicHttpContext();
        ctx.setAttribute(ClientContext.AUTH_CACHE, authCache);

        HttpPost post = new HttpPost("/tunnel-web/secure/json");
        
        System.out.println(form.getParameters().toString());
        
        post.setEntity(form.getHttpEntity());
        return httpClient.execute(targetHost, post, ctx);        
	}
	
	protected JSONObject parseResponseAsJSON(HttpResponse response) throws IOException, JSONException {
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		response.getEntity().writeTo(baos);
		
		String raw = baos.toString();
		
		System.out.println(raw);
		
		JSONObject json = new JSONObject(raw);		
		return json;
	}
	
	protected WebContentForm prepareForm(Long groupId, String title, String description, 
			String content, Date displayDate, Date expirationDate, 
			Date reviewDate, boolean indexable) {
		
		AddArticleForm form = new AddArticleForm();
        form.setArticleId(-1L);
        form.setTitle(title);
        form.setContent("Test plain text content");
        form.setGroupId(10156L);
        
        Calendar display = Calendar.getInstance();
        display.setTime(displayDate);
        
        Calendar expire = Calendar.getInstance();
        expire.setTime(expirationDate);
        
        Calendar review = Calendar.getInstance();
        review.setTime(reviewDate);
        
        form.setDescription(description)
        	.setDisplayDateDay(display.get(DAY_OF_MONTH))
        	.setDisplayDateMonth(display.get(MONTH))
        	.setDisplayDateYear(display.get(YEAR))
        	.setDisplayDateHour(display.get(HOUR))
        	.setDisplayDateMinute(display.get(MINUTE))
        	.setExpirationDateDay(expire.get(DAY_OF_MONTH))
        	.setExpirationDateMonth(expire.get(MONTH))
        	.setExpirationDateYear(expire.get(YEAR))
        	.setExpirationDateHour(expire.get(HOUR))
        	.setExpirationDateMinute(expire.get(MINUTE))
        	.setReviewDateDay(review.get(DAY_OF_MONTH))
        	.setReviewDateMonth(review.get(MONTH))
        	.setReviewDateYear(review.get(YEAR))
        	.setReviewDateHour(review.get(HOUR))
        	.setReviewDateMinute(review.get(MINUTE))
        	.setTemplateId("")
        	.setStructureId("")
        	.setServiceContext("{}")
        	.setArticleURL(title.replaceAll(" ", "_"))
        	.setNeverExpire(false) // determine these by whether each specific date is null or not
        	.setType("general")
        	.setNeverReview(false)
        	.setAutoArticleId(true)
        	.setIndexable(indexable);
        
        return form;
	}
	
}
