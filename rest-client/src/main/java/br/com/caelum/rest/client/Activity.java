package br.com.caelum.rest.client;

import br.com.caelum.rest.client.http.HttpMethod;

public class Activity {
	private final HttpMethod method;
	private final String uri;
	private final Integer responseCode;
	private final String location;

	public Activity(HttpMethod method, String uri, Integer responseCode, String location) {
		this.method = method;
		this.uri = uri;
		this.responseCode = responseCode;
		this.location = location;
	}

	public HttpMethod getMethod() {
		return method;
	}

	public String getUri() {
		return uri;
	}

	public Integer getResponseCode() {
		return responseCode;
	}

	public String getLocation() {
		return location;
	}

}
