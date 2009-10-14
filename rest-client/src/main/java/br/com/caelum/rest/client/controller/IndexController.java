package br.com.caelum.rest.client.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.httpclient.HttpClient;
import org.json.JSONException;
import org.json.JSONWriter;

import br.com.caelum.rest.client.http.HttpMethod;
import br.com.caelum.rest.client.http.HttpMethodWrapper;
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
	public void createSomething(HttpMethod method, String uri, String contentName, String contentValue) throws JSONException, IOException {
		HttpClient client = new HttpClient();

		HttpMethodWrapper httpMethod = method.getHttpMethod(uri);

		httpMethod.addParameter(contentName, contentValue);
		int resultCode = httpMethod.executeMethod(client);
		String response = httpMethod.getResponseBodyAsString();

		JSONWriter json = getWriter();

		json.key("response").value(response);
		json.key("responseCode").value(resultCode);
		json.key("method").value(method);
		json.key("uri").value(uri);
		if (resultCode == 201) {
			String location = httpMethod.getResponseHeader("location").getValue();
			json.key("location").value(location);
		}
		json.endObject();
	}

	@Path("/grab")
	@Get
	public void grab(String uri) throws JSONException, IOException {
		HttpClient client = new HttpClient();

		HttpMethodWrapper httpMethod = HttpMethod.GET.getHttpMethod(uri);

		int resultCode = httpMethod.executeMethod(client);
		String response = httpMethod.getResponseBodyAsString();

		JSONWriter json = getWriter();

		json.key("response").value(response);
		json.key("responseCode").value(resultCode);
		JSONWriter array = json.key("links").array();

		String[] lines = response.split("\n");
		for (String line : lines) {
			if(line.startsWith("<atom:link")) {
				String href = extractAttribute(line, "href");
				JSONWriter object = array.object();
				object.key("href").value(href);
				String rel = extractAttribute(line, "rel");
				object.key("rel").value(rel);
				object.endObject();

			}
		}
		array.endArray();
		json.key("uri").value(uri);
		json.endObject();
	}

	private String extractAttribute(String line, String attribute) {
		String base = " " + attribute+"=\"";
		int inicio = line.indexOf(base) + base.length();
		String href = line.substring(inicio, line.indexOf("\"", inicio + 1));
		return href;
	}

	private JSONWriter getWriter() throws JSONException, IOException {
		result.use(Results.nothing());
		return new JSONWriter(this.response.getWriter()).object();
	}

}
