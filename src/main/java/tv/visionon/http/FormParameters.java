package tv.visionon.http;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;

public class FormParameters {
	
	private final List<NameValuePair> parameters;
	
	public FormParameters() {
		this.parameters = new ArrayList<NameValuePair>();
	}
	
	public FormParameters addParameter(String name, Object value) {
		parameters.add(new BasicNameValuePair(name, String.valueOf(value)));
		return this;
	}
	
	public List<NameValuePair> getParameters() {
		return Collections.unmodifiableList(parameters);
	}
	
	public HttpEntity toEntity() {
		String encoding = HTTP.UTF_8; 
		try {
			return new UrlEncodedFormEntity(parameters, encoding);
		} catch(UnsupportedEncodingException uee) {
			throw new UnsupportedOperationException("Encoding '" + encoding + "' not supported on this platform!");
		}
	}
	
	public String toString() {
		return parameters.toString();
	}
}
