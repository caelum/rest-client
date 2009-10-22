package br.com.caelum.rest.client.http;

import java.io.IOException;

public interface HttpMethodWrapper {
	void addParameter(String parameterName, String parameterValue);

	int executeMethod() throws IOException ;

	String getResponseBodyAsString() throws IOException;

	String getResponseHeader(String header);
}
