package br.com.caelum.rest.client.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.json.JSONException;
import org.json.JSONWriter;

import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class IndexController {

	private final HttpServletResponse response;
	private final Result result;

	@Path("/")
	public void index() {

	}

	public IndexController(HttpServletResponse response, Result result) {
		this.response = response;
		this.result = result;
	}

	@Path("/createSomething")
	@Get
	public void createSomething(String uri, String contentName, String contentValue) throws HttpException,
			IOException, JSONException {
		HttpClient client = new HttpClient();
		PostMethod method = new PostMethod(uri);
		method.addParameter(contentName, contentValue);

		int resultCode = client.executeMethod(method);

		String response = method.getResponseBodyAsString();

		JSONWriter main = new JSONWriter(this.response.getWriter()).object();

		main.key("response").value(response);
		main.key("responseCode").value(resultCode);
		if (resultCode == 201) {
			String location = method.getResponseHeader("location").getValue();
			main.key("location").value(location);
		}
		main.endObject();
		result.use(Results.nothing());
	}
}
