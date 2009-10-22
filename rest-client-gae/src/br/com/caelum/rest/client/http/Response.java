package br.com.caelum.rest.client.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.util.List;
import java.util.Map;

public class Response {

	private int code;
	private String content;
	private Map<String, List<String>> headers;

	public Response(HttpURLConnection connection) throws IOException {
		this.code = connection.getResponseCode();
		this.content = connection.getContent().toString();
		this.headers = connection.getHeaderFields();
	}

	public int getCode() {
		return code;
	}

	public String getContent() {
		return content;
	}

	public List<String> getHeader(String key) {
		return headers.get(key);
	}
	
	

}
