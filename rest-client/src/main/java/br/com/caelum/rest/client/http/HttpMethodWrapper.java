package br.com.caelum.rest.client.http;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;

public interface HttpMethodWrapper {
	void addParameter(String parameterName, String parameterValue);

	int executeMethod(HttpClient client) throws HttpException;

	String getResponseBodyAsString() throws IOException;

	Header getResponseHeader(String string);
}
