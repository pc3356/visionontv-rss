/**
 * 
 */
package tv.visionon.http;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;

/**
 * @author administrator
 *
 */
public abstract class WebContentForm {

	private final static String SERVICE_CLASS_NAME = "com.liferay.portlet.journal.service.JournalArticleServiceUtil";
	
	private final static String UPDATE_ARTICLE = "updateArticle";
	private final static String DELETE_ARTICLE = "deleteArticle";
	
	private String serviceMethodName;
	private String serviceParameters;
	private String serviceParameterTypes;
	
	protected FormParameters parameters;
	
	protected String action;
	protected Long groupId;
	protected Long articleId;
	protected String title;
	protected String content;
	
	protected Map<String, String> parameterMapping;
	
	public WebContentForm(String serviceMethodName, String serviceParameters, String serviceParameterTypes) {
		
		this.parameters = new FormParameters();
		
		this.serviceMethodName = serviceMethodName;
		this.serviceParameters = serviceParameters;
		this.serviceParameterTypes = serviceParameterTypes;
		
		parameterMapping = new HashMap<String, String>();
		String[] parameterNames = serviceParameters.substring(1, serviceParameters.length() - 1).split(",");
		String[] parameterTypes = serviceParameterTypes.substring(1, serviceParameterTypes.length() - 1).split(",");
		for(int i = 0; i < parameterNames.length; i++) {
			parameterMapping.put(parameterNames[i], parameterTypes[i]);
		}
		
		addParameter("serviceClassName", SERVICE_CLASS_NAME);
		addParameter("serviceMethodName", serviceMethodName);
		addParameter("serviceParameters", serviceParameters);
		addParameter("serviceParameterTypes", serviceParameterTypes);
	}
	
	protected void addParameter(String name, Object value) {
		parameters.addParameter(name, value);
	}
	
	public String getTitle() {
		return title;
	}
	
	public void setTitle(String title) {
		this.title = title;
		addParameter("title", title);
	}	

	public Long getGroupId() {
		return groupId;
	}

	public void setGroupId(Long groupId) {
		this.groupId = groupId;
		addParameter("groupId", groupId);
	}

	public Long getArticleId() {
		return articleId;
	}

	public void setArticleId(Long articleId) {
		this.articleId = articleId;
		addParameter("articleId", articleId);
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
		addParameter("content", content);
	}

	public String getServiceMethodName() {
		return serviceMethodName;
	}
	
	public String getServiceParameters() {
		return serviceParameters;
	}
	
	public String getServiceParameterTypes() {
		return serviceParameterTypes;
	}
	
	public FormParameters getParameters() {
		return parameters;
	}
	
	public HttpEntity getHttpEntity() {
		validateParameters();
		return parameters.toEntity();
	}
	
	private void validateParameters() {
		Set<String> expectedParameterNames = parameterMapping.keySet();
		Set<String> actualParameterNames = new HashSet<String>();
		for(NameValuePair nvp : getParameters().getParameters()) {
			String name = nvp.getName();
			if(actualParameterNames.contains(name)) {
				throw new UnsupportedOperationException("Parameter map already contains the key " + name);
			}
			actualParameterNames.add(name);
		}
		
		if(expectedParameterNames.containsAll(actualParameterNames) && actualParameterNames.containsAll(expectedParameterNames)) {
			return;
		} else {
			int actualSize = actualParameterNames.size();
			int expectedSize = expectedParameterNames.size();
			
			for(String expected :  expectedParameterNames) {
				if(!actualParameterNames.contains(expected)) {
					System.err.println("Expected key missing '" + expected + "'");
				}
			}
			
			for(String actual : actualParameterNames) {
				if(!expectedParameterNames.contains(actual)) {
					System.err.println("Additional key found '" + actual + "'");
				}
			}
			
			//throw new IllegalArgumentException("Actual and expected parameters don't match! Actual " + actualSize + ", expected " + expectedSize);
		}
	}
}
