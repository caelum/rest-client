package br.com.caelum.rest.client.http;


public enum HttpMethod {
	POST {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return new PostMethodWrapper(uri, false);
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
			return new GetMethodWrapper((uri));
		}
	},
	DELETE {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return new DeleteMethodWrapper((uri));
		}
	},
	OPTIONS {
		@Override
		public HttpMethodWrapper getHttpMethod(String uri) {
			return null;
		}
	};

	public abstract HttpMethodWrapper getHttpMethod(String uri);

}
