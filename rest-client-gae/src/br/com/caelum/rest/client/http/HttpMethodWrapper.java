package br.com.caelum.rest.client.http;

import java.io.IOException;

import org.apache.http.Header;
import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;

public interface HttpMethodWrapper {
	void addParameter(String parameterName, String parameterValue);

	int executeMethod(HttpClient client) throws HttpException;

	String getResponseBodyAsString() throws IOException;

	String getResponseHeader(String header);
}
