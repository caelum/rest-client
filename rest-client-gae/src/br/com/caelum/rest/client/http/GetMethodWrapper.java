package br.com.caelum.rest.client.http;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.http.HttpException;
import org.apache.http.client.HttpClient;

public class GetMethodWrapper implements HttpMethodWrapper {

	private Response response;
	private String uri;

	public GetMethodWrapper(String uri) {
		this.uri = uri + "?";
	}

	public void addParameter(String parameterName, String parameterValue) {
		uri += parameterName + "=" + parameterValue + "&";
	}

	public int executeMethod(HttpClient client) throws HttpException {
        try {
            URL url = new URL(uri);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(false);
            connection.setRequestMethod("GET");

            this.response = new Response(connection);
            return response.getCode();

        } catch (MalformedURLException e) {
        	throw new HttpException(e.getMessage(),e);
        } catch (IOException e) {
        	throw new HttpException(e.getMessage(),e);
        }
	}

	private String getFullUri() {
		return uri;
	}

	public String getResponseBodyAsString() throws IOException{ 
		 return response.getContent();
	}

	public String getResponseHeader(String headerName) {
		return response.getHeader(headerName).get(0);
	}
}
