package br.com.caelum.rest.client.http;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;

public class PostMethodWrapper implements HttpMethodWrapper {

	private final String uri;
	private Map<String,String> params = new HashMap<String,String>();
	private Response response;

	public PostMethodWrapper(String uri) {
		this.uri = uri;
	}

	public void addParameter(String parameterName, String parameterValue) {
		params.put(parameterName, parameterValue);
	}

	public int executeMethod(HttpClient client) throws HttpException {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("POST");
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
            for(String key : params.keySet()) {
            	writer.write(key + "=" + params.get(key) + "\n");
            }
            writer.close();

            this.response = new Response(connection);
            return response.getCode();

        } catch (MalformedURLException e) {
        	throw new HttpException(e.getMessage(),e);
        } catch (IOException e) {
        	throw new HttpException(e.getMessage(),e);
        }
	}

	public String getResponseBodyAsString() throws IOException{ 
		 return response.getContent();
	}

	public String getResponseHeader(String headerName) {
		return response.getHeader(headerName).get(0);
	}
}
