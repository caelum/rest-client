package br.com.caelum.rest.client.http;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;

public class GetMethodWrapper implements HttpMethodWrapper {

	private final GetMethod method;
	private final HttpMethodParams params;

	public GetMethodWrapper(GetMethod method) {
		this.method = method;
		params = new HttpMethodParams();
	}

	public void addParameter(String parameterName, String parameterValue) {
		params.setParameter(parameterName, parameterValue);
	}

	public int executeMethod(HttpClient client) throws HttpException {
		method.setParams(params);
		try {
			return client.executeMethod(method);
		} catch (IOException e) {
			e.printStackTrace();
			throw new HttpException("The HTTP request failed, check the stack trace for more details", e);
		}
	}

	public String getResponseBodyAsString() throws IOException {
		return method.getResponseBodyAsString();
	}

	public Header getResponseHeader(String headerName) {
		return method.getResponseHeader(headerName);
	}
}
