package br.com.caelum.rest.client.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class PostMethodWrapper implements HttpMethodWrapper {

	private final String uri;
	private Map<String,String> params = new HashMap<String,String>();
	private Response response;
	private final boolean shouldReadContent;

	public PostMethodWrapper(String uri, boolean shouldReadContent) {
		this.uri = uri;
		this.shouldReadContent = shouldReadContent;
	}

	public void addParameter(String parameterName, String parameterValue) {
		params.put(parameterName, parameterValue);
	}

	public int executeMethod() throws IOException{
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            for(String key : params.keySet()) {
            	writer.write(key + "=" + params.get(key) + "\n");
            }
            writer.close();

            this.response = new Response(connection, shouldReadContent);
            return response.getCode();

	}

	public String getResponseBodyAsString() throws IOException{ 
		 return response.getContent();
	}

	public String getResponseHeader(String headerName) {
		return response.getHeader(headerName).get(0);
	}
}
