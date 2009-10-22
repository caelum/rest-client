package br.com.caelum.rest.server;

import javax.servlet.http.HttpServletRequest;

public class SimpleAction implements Action {

	public String getUri() {
		return uri;
	}

	public String getRel() {
		return rel;
	}

	private final String uri;
	private final String rel;

	public SimpleAction(String rel, String uri) {
		this.rel = rel;
		this.uri = uri;
	}
	
	public SimpleAction(String rel, HttpServletRequest request, String uri) {
		this.rel = rel;
		this.uri  = "http://restful-server.appspot.com" + uri;
		// this.uri = "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + uri;
		
	}

}
