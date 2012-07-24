/**
 * 
 */
package tv.visionon.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.protocol.HTTP;

/**
 * @author pjc
 *
 */
public class LoginForm implements LiferayForm {
	private String action;
	private NameValuePair login;
	private NameValuePair password;
	
	public LoginForm(String action, NameValuePair login, NameValuePair password) {
		super();
		this.action = action;
		this.login = login;
		this.password = password;
	}

	/* (non-Javadoc)
	 * @see LiferayForm#getAction()
	 */
	public String getAction() {
		return action;
	}

	public NameValuePair getLogin() {
		return login;
	}

	public NameValuePair getPassword() {
		return password;
	}
	
	public String toString() {
		return action + "\n" + login.getName() + "\n" + password.getName();
	}
	
	public HttpEntity toEntity() throws UnsupportedEncodingException {
		List<NameValuePair> nvps = new ArrayList <NameValuePair>();
		nvps.add(getLogin());
		nvps.add(getPassword());
		return new UrlEncodedFormEntity(nvps, HTTP.UTF_8);
	}
}
