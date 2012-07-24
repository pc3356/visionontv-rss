package tv.visionon.http;

import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.DAY_OF_YEAR;
import static java.util.Calendar.HOUR;
import static java.util.Calendar.MINUTE;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.WEEK_OF_YEAR;
import static java.util.Calendar.YEAR;
import static java.util.Calendar.getInstance;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.AuthCache;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.auth.BasicScheme;
import org.apache.http.impl.client.BasicAuthCache;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;

public class DocumentPoster
{

	private final static String userEmail = "xyz@gmail.com";
	private final static String userName = "xyz";
	private final static String password = "kyiuhjkhu";
	private final static String baseUrl = "82.35.198.188";
	
	public static void main(String[] args) throws Exception {
		DefaultHttpClient httpClient = new DefaultHttpClient();
		try {
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.DAY_OF_YEAR, -1);
			Date displayDate = calendar.getTime();
			calendar.add(Calendar.WEEK_OF_YEAR, 1);
			Date reviewDate = calendar.getTime();
			Date expirationDate = calendar.getTime();
			AddArticleServiceImpl addArticleService = new AddArticleServiceImpl(httpClient);
			String articleId = addArticleService.addArticle(10156L, 
					UUID.randomUUID() + " : " + new Date(), 
					"A programmatically published article", 
					"<?xml version='1.0' encoding='UTF-8'?>" + 
						"<root available-locales=\"en_US\" default-locale=\"en_US\">" + 
        				"<static-content language-id=\"en_US\"><![CDATA[<p> " +
        				"changed content" + new Date() + " : " + UUID.randomUUID() + "</p>]]></static-content></root>", 
					displayDate, 
					expirationDate, 
					reviewDate, 
					true);
			
			System.out.println("Added article " + articleId);
			
			//addArticle(httpClient);
			//updateContent(httpClient);
			
//			LiferayLogin login = new LiferayLogin(userEmail, password, baseUrl);
//			login.setHostUri(baseUrl);
//			login.setHttpClient(httpClient);
//			login.execute(userEmail, password);
		}
		finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public static void updateContent(DefaultHttpClient httpClient) throws Exception {
        //HttpHost targetHost = new HttpHost("localhost", 8080, "http");
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
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        Calendar nextWeek = Calendar.getInstance();
        nextWeek.add(Calendar.WEEK_OF_YEAR, 1);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("serviceClassName", "com.liferay.portlet.journal.service.JournalArticleServiceUtil"));
        params.add(new BasicNameValuePair("serviceMethodName", "updateContent"));
        params.add(new BasicNameValuePair("serviceParameters", "[groupId,articleId,version,content]"));
        params.add(new BasicNameValuePair("serviceParameterTypes", "[long,java.lang.String,double,java.lang.String]"));
        params.add(new BasicNameValuePair("groupId", "10156"));
        params.add(new BasicNameValuePair("articleId", "36402"));
        params.add(new BasicNameValuePair("version", "1"));
        params.add(new BasicNameValuePair("content", "<?xml version='1.0' encoding='UTF-8'?>" + 
        		"<root available-locales=\"en_US\" default-locale=\"en_US\">" + 
        		"<static-content language-id=\"en_US\"><![CDATA[<p> " +
        		"changed content" + new Date() + " : " + UUID.randomUUID() + "</p>]]></static-content></root>"));
        // long groupId, java.lang.String articleId, double version, java.lang.String content
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);
        HttpResponse resp = httpClient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());
        resp.getEntity().writeTo(System.out);
	}
	
	public static void addArticle(DefaultHttpClient httpClient) throws Exception {
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
        Calendar yesterday = getInstance();
        yesterday.add(DAY_OF_YEAR, -1);
        Calendar nextWeek = getInstance();
        nextWeek.add(WEEK_OF_YEAR, 1);
        
        String title = UUID.randomUUID() + " : " + new Date();
        AddArticleForm form = new AddArticleForm();
        form.setArticleId(-1L);
        form.setTitle(title);
        form.setContent("Test plain text content");
        form.setGroupId(10156L);
        
        form.setDescription("A simple sample description")
        	.setDisplayDateDay(yesterday.get(DAY_OF_MONTH))
        	.setDisplayDateMonth(yesterday.get(MONTH))
        	.setDisplayDateYear(yesterday.get(YEAR))
        	.setDisplayDateHour(yesterday.get(HOUR))
        	.setDisplayDateMinute(yesterday.get(MINUTE))
        	.setExpirationDateDay(nextWeek.get(DAY_OF_MONTH))
        	.setExpirationDateMonth(nextWeek.get(MONTH))
        	.setExpirationDateYear(nextWeek.get(YEAR))
        	.setExpirationDateHour(nextWeek.get(HOUR))
        	.setExpirationDateMinute(nextWeek.get(MINUTE))
        	.setReviewDateDay(nextWeek.get(DAY_OF_MONTH))
        	.setReviewDateMonth(nextWeek.get(MONTH))
        	.setReviewDateYear(nextWeek.get(YEAR))
        	.setReviewDateHour(nextWeek.get(HOUR))
        	.setReviewDateMinute(nextWeek.get(MINUTE))
        	.setTemplateId("")
        	.setStructureId("")
        	.setServiceContext("{}")
        	.setArticleURL(title.replaceAll(" ", "_"))
        	.setNeverExpire(false)
        	.setType("general")
        	.setNeverReview(false)
        	.setAutoArticleId(true)
        	.setIndexable(true);
        
        System.out.println(form.getParameters().toString());
        
        post.setEntity(form.getHttpEntity());
        HttpResponse resp = httpClient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());
        System.out.println("Output:\n");
        resp.getEntity().writeTo(System.out);        
        System.out.println("\n\nEOF");
	}
	
	public static void addArticleUsingSimpleMechanism(DefaultHttpClient httpClient) throws Exception {
        //HttpHost targetHost = new HttpHost("localhost", 8080, "http");
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
        Calendar yesterday = Calendar.getInstance();
        yesterday.add(Calendar.DAY_OF_YEAR, -1);
        Calendar nextWeek = Calendar.getInstance();
        nextWeek.add(Calendar.WEEK_OF_YEAR, 1);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("serviceClassName", "com.liferay.portlet.journal.service.JournalArticleServiceUtil"));
        params.add(new BasicNameValuePair("serviceMethodName", "addArticle"));
        params.add(new BasicNameValuePair("serviceParameters", "[groupId,articleId,autoArticleId,title,description,content,type,structureId,templateId,displayDateMonth,displayDateDay,displayDateYear,displayDateHour,displayDateMinute,expirationDateMonth,expirationDateDay,expirationDateYear,expirationDateHour,expirationDateMinute,neverExpire,reviewDateMonth,reviewDateDay,reviewDateYear,reviewDateHour,reviewDateMinute,neverReview,indexable,articleURL,serviceContext]"));
        params.add(new BasicNameValuePair("serviceParameterTypes", "[long,java.lang.String,boolean,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,java.lang.String,int,int,int,int,int,int,int,int,int,int,boolean,int,int,int,int,int,boolean,boolean,java.lang.String,com.liferay.portal.service.ServiceContext]"));
        params.add(new BasicNameValuePair("groupId", "10156"));
        params.add(new BasicNameValuePair("articleId", "-1"));
        params.add(new BasicNameValuePair("autoArticleId", "true"));
        params.add(new BasicNameValuePair("title", UUID.randomUUID() + " : " + new Date()));
        params.add(new BasicNameValuePair("description", "Test JSON Description"));
        params.add(new BasicNameValuePair("content", "<?xml version='1.0' encoding='UTF-8'?><root available-locales=\"en_US\" default-locale=\"en_US\"><static-content language-id=\"en_US\"><![CDATA[<p>\n" +
                "\t" + UUID.randomUUID() + " : " + new Date() + "</p>]]></static-content></root>"));
        params.add(new BasicNameValuePair("type", "general"));
        params.add(new BasicNameValuePair("structureId", ""));
        params.add(new BasicNameValuePair("templateId", ""));
        params.add(new BasicNameValuePair("displayDateMonth", "" + (1 + yesterday.get(Calendar.MONTH))));
        params.add(new BasicNameValuePair("displayDateDay", "" + yesterday.get(Calendar.DAY_OF_MONTH)));
        params.add(new BasicNameValuePair("displayDateYear", "" + yesterday.get(Calendar.YEAR)));
        params.add(new BasicNameValuePair("displayDateHour", "" + yesterday.get(Calendar.HOUR_OF_DAY)));
        params.add(new BasicNameValuePair("displayDateMinute", "" + yesterday.get(Calendar.MINUTE)));
        params.add(new BasicNameValuePair("expirationDateMonth", "" + (1 + nextWeek.get(Calendar.MONTH))));
        params.add(new BasicNameValuePair("expirationDateDay", "" + nextWeek.get(Calendar.DAY_OF_MONTH)));
        params.add(new BasicNameValuePair("expirationDateYear", "" + nextWeek.get(Calendar.YEAR)));
        params.add(new BasicNameValuePair("expirationDateHour", "" + nextWeek.get(Calendar.HOUR_OF_DAY)));
        params.add(new BasicNameValuePair("expirationDateMinute", "" + nextWeek.get(Calendar.MINUTE)));
        params.add(new BasicNameValuePair("neverExpire", "false"));
        params.add(new BasicNameValuePair("reviewDateMonth", "" + (1 + nextWeek.get(Calendar.MONTH))));
        params.add(new BasicNameValuePair("reviewDateDay", "" + nextWeek.get(Calendar.DAY_OF_MONTH)));
        params.add(new BasicNameValuePair("reviewDateYear", "" + nextWeek.get(Calendar.YEAR)));
        params.add(new BasicNameValuePair("reviewDateHour", "" + nextWeek.get(Calendar.HOUR_OF_DAY)));
        params.add(new BasicNameValuePair("reviewDateMinute", "" + nextWeek.get(Calendar.MINUTE)));
        params.add(new BasicNameValuePair("neverReview", "false"));
        params.add(new BasicNameValuePair("indexable", "true"));
        params.add(new BasicNameValuePair("articleURL", "articleURL"));
        params.add(new BasicNameValuePair("serviceContext", "{}"));
        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);
        HttpResponse resp = httpClient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());
        resp.getEntity().writeTo(System.out);
    }

    public static void removeArticle(DefaultHttpClient httpClient) throws Exception {
        HttpHost targetHost = new HttpHost("localhost", 8080, "http");
        httpClient.getCredentialsProvider().setCredentials(
                new AuthScope(targetHost.getHostName(), targetHost.getPort()),
                new UsernamePasswordCredentials("test", "test"));

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
        Calendar now = Calendar.getInstance();
        Calendar nextWeek = Calendar.getInstance();
        nextWeek.add(Calendar.WEEK_OF_YEAR, 1);
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("serviceClassName", "com.liferay.portlet.journal.service.JournalArticleServiceUtil"));
        params.add(new BasicNameValuePair("serviceMethodName", "deleteArticle"));
        params.add(new BasicNameValuePair("serviceParameterTypes", "[long,java.lang.String,java.lang.String,com.liferay.portal.service.ServiceContext]"));
        params.add(new BasicNameValuePair("serviceParameters", "[groupId,articleId,articleURL,serviceContext]"));
        params.add(new BasicNameValuePair("groupId", "10156"));
        params.add(new BasicNameValuePair("articleId", "60000"));
        params.add(new BasicNameValuePair("articleURL", "articleURL"));
        params.add(new BasicNameValuePair("serviceContext", "{}"));

        UrlEncodedFormEntity entity = new UrlEncodedFormEntity(params, "UTF-8");
        post.setEntity(entity);
        HttpResponse resp = httpClient.execute(targetHost, post, ctx);
        System.out.println(resp.getStatusLine());
        resp.getEntity().writeTo(System.out);
    }


}
