package br.com.caelum.rest.client.http;

public enum HttpMethod {
	POST {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return null;
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
