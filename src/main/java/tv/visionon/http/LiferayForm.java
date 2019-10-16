package tv.visionon.http;

import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;

public interface LiferayForm {
	String getAction();
	HttpEntity toEntity() throws UnsupportedEncodingException;
}