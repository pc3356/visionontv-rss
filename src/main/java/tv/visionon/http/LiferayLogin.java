package tv.visionon.http;

import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class LiferayLogin {
	
	private HttpClient httpClient;
	private String hostUri;
	private final static String LOGIN_PATH = "/c/portal/login";
	
	private final String username;
	private final String password;
	private final String baseUrl;
	
	public LiferayLogin(String username, String password, String baseUrl) {
		this.username = username;
		this.password = password;
		this.baseUrl = baseUrl;
	}
	
	public void setHttpClient(HttpClient httpClient) {
		this.httpClient = httpClient;
	}
	
	public void setHostUri(String hostUri) {
		this.hostUri = hostUri;
	}
	
	public void execute(String userEmail, String password) throws Exception {
		
		getLoginForm();
		
	}
	
	private LoginForm getLoginForm() throws Exception {
		HttpGet get = new HttpGet(hostUri + LOGIN_PATH);
		HttpResponse response = httpClient.execute(get);
		HttpEntity entity = response.getEntity();
		
		String content = EntityUtils.toString(entity);
		
		Document document = LiferayFormUtils.convertDocumentFromHtml(content);
		NodeList forms = LiferayFormUtils.extractForms(document);
		Node loginFormNode = getLoginFormNode(forms);
		
		String action = "";
		String userNameField = "";
		String passwordField = "";
		
		return new LoginForm(action, new BasicNameValuePair(userNameField, username), new BasicNameValuePair(passwordField, password));
	}
	
	private Node getLoginFormNode(NodeList allForms) throws Exception {
		for(int i = 0; i < allForms.getLength(); i++) {
			Node formNode = allForms.item(i);
			Map<String, String> attributes = LiferayFormUtils.getFormAttributes(formNode);
			for(String key : attributes.keySet()) {
				
				String rawValue = attributes.get(key);
				String value = ("action".equalsIgnoreCase(key) ? 
						LiferayFormUtils.revertXmlEscapedQueryString(rawValue) : 
							rawValue);
				if("id".equalsIgnoreCase(key)) {
					if(value.matches("_\\d{1,3}_fm")) {
						return formNode;
					}
				}
			}
		}
		throw new Exception("No matching form found");
	}		
}
