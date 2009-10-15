package br.com.caelum.rest.client.http;

import java.io.IOException;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;

public class DeleteMethodWrapper implements HttpMethodWrapper {

	private final DeleteMethod method;

	public DeleteMethodWrapper(DeleteMethod deleteMethod) {
		this.method = deleteMethod;
	}

	@Override
	public void addParameter(String parameterName, String parameterValue) {

	}

	@Override
	public int executeMethod(HttpClient client) throws HttpException {
		try {
			return client.executeMethod(method);
		} catch (IOException e) {
			throw new HttpException("", e);
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
