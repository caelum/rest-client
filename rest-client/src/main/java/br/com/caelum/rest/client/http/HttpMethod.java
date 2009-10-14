package br.com.caelum.rest.client.http;

import org.apache.commons.httpclient.methods.PostMethod;

public enum HttpMethod {
	POST {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return new PostMethodWrapper(new PostMethod(uri));
		}
	},
	PUT {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return null;
		}
	},
	GET {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return null;
		}
	},
	DELETE {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return null;
		}
	};

	public abstract HttpMethodWrapper getHttpMethod(String uri);

}
