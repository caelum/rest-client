package br.com.caelum.rest.client.http;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;

public class PostMethodWrapper implements HttpMethodWrapper {

	private final PostMethod method;

	public PostMethodWrapper(PostMethod method) {
		this.method = method;
	}

	public void addParameter(String parameterName, String parameterValue) {
		method.addParameter(parameterName, parameterValue);
	}

	@Override
	public int executeMethod(HttpClient client) throws HttpException {
		try {
			return client.executeMethod(method);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException("The HTTP request failed, check the stack trace for more details", e);
		}
	}

	@Override
	public String getResponseBodyAsString() throws IOException {
		return method.getResponseBodyAsString();
	}

	@Override
	public Header getResponseHeader(String headerName) {
		return method.getResponseHeader(headerName);
	}
}
