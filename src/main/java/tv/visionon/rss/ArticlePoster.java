/**
 * 
 */
package tv.visionon.rss;

import static tv.visionon.http.LiferayFormUtils.extractFormAction;
import static tv.visionon.http.LiferayFormUtils.getAuthId;
import static tv.visionon.http.LiferayFormUtils.getFormCollationId;
import static tv.visionon.http.LiferayFormUtils.getFormFields;
import static tv.visionon.http.LiferayFormUtils.getInputFields;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.cookie.Cookie;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import tv.visionon.http.LiferayForm;
import tv.visionon.http.LiferayFormUtils;
import tv.visionon.http.LoginForm;

/**
 * @author pjc
 *
 */
public class ArticlePoster {

	private String userName = "rss"; // won't be used for now
	private String userEmail = "pjcoates1976@gmail.com";
	private String password = "GU3bh6Y6";
	private String baseUrl = "http://92.239.225.98";
	private String loginPath = "/c/portal/login";
	private String logoutPath = "/c/portal/logout";
	private Long groupId = 10156L;
	private String publishContentPath = "";
	private DefaultHttpClient httpClient;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		ArticlePoster poster = new ArticlePoster();
		poster.postArticle();
	}
	
	public ArticlePoster() {
		this.httpClient = new DefaultHttpClient();
	}
	
	public void postArticle() throws Exception {
		try {
			
			
			LoginForm loginForm = getLoginPage();
			doLogin(loginForm);
			
			System.out.println("Post logon cookies:");
			listCookies();
	        
	        // get web content entry form			
			LiferayForm webContentForm = getWebContentForm();
	        
			HttpPost webContentPost = new HttpPost(webContentForm.getAction());
			webContentPost.setEntity(webContentForm.toEntity());
			HttpResponse webFormResponse = httpClient.execute(webContentPost);
	        
		}
		finally {
			httpClient.getConnectionManager().shutdown();
		}
	}
	
	public LoginForm getLoginPage() throws Exception {
		HttpGet get = new HttpGet(baseUrl + loginPath);
		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		
		String content = EntityUtils.toString(entity);
		
		performXHtmlOperations("getLoginPage", content);
		
		// extract FORMs		
		List<String> forms = getFormFields(content);
		List<String> inputs = getInputFields(content);
		
		int collationId = getFormCollationId(forms);
		String action = extractFormAction(forms);
		NameValuePair loginField = new BasicNameValuePair("_" + collationId + "_login", userEmail);
		NameValuePair passwordField = new BasicNameValuePair("_" + collationId + "_password", password);
		return new LoginForm(action, loginField, passwordField);
	}
	
	public void doLogin(final LoginForm loginForm) throws Exception {
		// create request for login form
		HttpPost post = new HttpPost(loginForm.getAction());
		
        post.setEntity(loginForm.toEntity());

        System.out.println("Ready to send");
        HttpResponse response = httpClient.execute(post);
        HttpEntity entity = response.getEntity();

        System.out.println("Login form get: " + response.getStatusLine());
        EntityUtils.consume(entity);        
	}
	
	public LiferayForm getWebContentForm() throws Exception {
		String webContentUrl = loadWebContentPageUrl();
		System.out.println("WCU: " + webContentUrl);
		HttpGet get = new HttpGet(webContentUrl);		
		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String formPageContent = EntityUtils.toString(entity);

		performXHtmlOperations("getWebContentForm", formPageContent);
		
		List<String> formTags = LiferayFormUtils.getFormFields(formPageContent);
		//String action = LiferayFormUtils.extractFormAction(formTags);
		String action = webContentUrl;
		
		String titleInputName = "_15_title";
		String contentInputName = "_15_content";
		String tagInputName = "";
		
		NameValuePair title = new BasicNameValuePair(titleInputName, "Title " + new Date());
		NameValuePair content = new BasicNameValuePair(contentInputName, "Content " + new Date());
		NameValuePair[] tags = {new BasicNameValuePair(tagInputName, "Tag " + new Date())};
		System.out.println("Action: " + action);
		return null;//new WebContentForm(action, title, content, tags);
	}

	public void performXHtmlOperations(String operationName, String content) throws Exception {
		Document document = LiferayFormUtils.convertDocumentFromHtml(content);
		NodeList forms = LiferayFormUtils.extractForms(document);
		System.out.println("====================");
		System.out.println(operationName);
		System.out.println("====================");
		for(int i = 0; i < forms.getLength(); i++) {
			System.out.println("~~~~~~~~~~~~~~~~~~");
			Node formNode = forms.item(i);
			Map<String, String> attributes = LiferayFormUtils.getFormAttributes(formNode);
			for(String key : attributes.keySet()) {
				String rawValue = attributes.get(key);
				String value = ("action".equalsIgnoreCase(key) ? 
						LiferayFormUtils.revertXmlEscapedQueryString(rawValue) : 
							rawValue);
				System.out.println(key + " : " + value);
			}
			System.out.println("~~~~~~~~~~~~~~~~~~");
		}
		System.out.println("====================");
	}
	
	public void doLogout() throws Exception {
		
	}
	
	private String loadWebContentPageUrl() throws Exception {
		
		// http://92.239.225.98/web/guest/members?p_p_auth=4cUyoGwu&p_p_id=15&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&p_p_col_id=column-2&p_p_col_pos=2&p_p_col_count=4&_15_struts_action=%2Fjournal%2Fedit_article&_15_groupId=10156&_15_redirect=%2Fweb%2Fguest%2Fmembers&_15_backURL=%2Fweb%2Fguest%2Fmembers&_15_referringPortletResource=101_INSTANCE_Jj3k&_15_assetTagNames=
		
		String webContentPageUrl = "http://92.239.225.98/web/guest/members";
		HttpGet get = new HttpGet(webContentPageUrl);
		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		String content = EntityUtils.toString(entity);
		//EntityUtils.consume(entity);
		String authId = getAuthId(content);
		
		Integer collationId = Integer.valueOf(15); // derive this
		
		List<? extends NameValuePair> fullWebContentUrl = buildFullWebContentUrlParameters(collationId, authId);
		
		HttpGet retrieveForm = new HttpGet(webContentPageUrl);
		//String params = URLEncodedUtils.format(fullWebContentUrl, HTTP.UTF_8);
		
		//System.out.println(params);
		
		
		return webContentPageUrl + "?" + StringUtils.join(fullWebContentUrl, "&");
	}
	
	private void instructs() {
		// get web content form
        // http://92.239.225.98/web/guest/members?p_p_auth=iADKv0IV&p_p_id=15&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&p_p_col_id=column-1&_15_struts_action=%2Fjournal%2Fedit_article&_15_groupId=10156&_15_redirect=%2Fweb%2Fguest%2Fmembers&_15_backURL=%2Fweb%2Fguest%2Fmembers&_15_referringPortletResource=101_INSTANCE_Jj3k&_15_assetTagNames=
        // http://92.239.225.98/web/guest/members?p_p_auth=iADKv0IV&p_p_id=15&p_p_lifecycle=0&p_p_state=maximized&p_p_mode=view&p_p_col_id=column-2&p_p_col_pos=2&p_p_col_count=4&_15_struts_action=%2Fjournal%2Fedit_article&_15_groupId=10156&_15_redirect=%2Fweb%2Fguest%2Fmembers%3Fp_p_id%3D15%26p_p_mode%3Dview%26_15_struts_action%3D%252Fjournal%252Fedit_article%26_15_groupId%3D10156&_15_backURL=%2Fweb%2Fguest%2Fmembers%3Fp_p_id%3D15%26p_p_mode%3Dview%26_15_struts_action%3D%252Fjournal%252Fedit_article%26_15_groupId%3D10156&_15_referringPortletResource=101_INSTANCE_Jj3k&_15_assetTagNames=
        // http://92.239.225.98/web/guest/members
        
        // first identify auth id
        // next compose URL to this new form
        // then populate the form and post
	}
	
	private void listCookies() {
		List<Cookie> cookies = httpClient.getCookieStore().getCookies();
        if (cookies.isEmpty()) {
            System.out.println("None");
        } else {
            for (Cookie cookie : cookies) {
            	if("JSESSIONID".equalsIgnoreCase(cookie.getName())) {
            		System.out.println(cookie.getName() + " : " + cookie.getValue());
            	}
            }
        }
	}
	
	private List<? extends NameValuePair> buildFullWebContentUrlParameters(Integer collationId, String authId) {
		// http://92.239.225.98/web/guest/members
		// p_p_auth=iADKv0IV
		// p_p_id=15
		// p_p_lifecycle=0 
		// p_p_state=maximized 
		// p_p_mode=view
		// p_p_col_id=column-1
		// _15_struts_action=%2Fjournal%2Fedit_article
		// _15_groupId=10156
		// _15_redirect=%2Fweb%2Fguest%2Fmembers
		// _15_backURL=%2Fweb%2Fguest%2Fmembers
		// _15_referringPortletResource=101_INSTANCE_Jj3k
		// _15_assetTagNames=
		// build parameters for initial request
		return Arrays.asList(
			new BasicNameValuePair("p_p_auth", authId),
			new BasicNameValuePair("p_p_id", collationId.toString()), // derive collation id
			new BasicNameValuePair("p_p_lifecycle", "0"),
			new BasicNameValuePair("p_p_state", "maximized"),
			new BasicNameValuePair("p_p_mode", "view"),
			new BasicNameValuePair("p_p_col_id", "column-1"),
			new BasicNameValuePair("_" + collationId + "_struts_action", "%2Fjournal%2Fedit_article"),
			new BasicNameValuePair("_" + collationId + "_groupId", groupId.toString()),
			new BasicNameValuePair("_" + collationId + "_redirect", "%2Fweb%2Fguest%2Fmembers"),
			new BasicNameValuePair("_" + collationId + "_backURL", "%2Fweb%2Fguest%2Fmembers"),
			new BasicNameValuePair("_" + collationId + "_referringPortletResource", "101_INSTANCE_Jj3k"), // get instance ID
			new BasicNameValuePair("_" + collationId + "_assetTagNames", "")
		);
	}
}
