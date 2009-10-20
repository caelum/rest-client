package br.com.caelum.rest.server;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.caelum.vraptor.Delete;
import br.com.caelum.vraptor.Get;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.view.Results;

@Resource
public class OrderController {

	private final Result result;
	private final Router route;
	private final HttpServletRequest request;
	private final LinkAsAtom linker;

	public OrderController(Result result, Router route, HttpServletRequest request, LinkAsAtom linker) {
		this.result = result;
		this.route = route;
		this.request = request;
		this.linker = linker;
	}

	private static Map<Long, Order> orders = new HashMap<Long, Order>();

	@Path("/order/{id}")
	@Get
	public void getOrder(Long id) throws SecurityException, NoSuchMethodException {

		Order order = orders.get(id);
		
		if(order == null) {
			result.use(Results.http()).setStatusCode(HttpServletResponse.SC_NOT_FOUND);
		} else {
			String xml = order.toXml(linker);
			result.include("order", xml);
		}
	}

	@Path("/order")
	@Post
	public void createOrder(String content) {
		long novoId = orders.size() + 1;
		orders.put(novoId, new Order(novoId, content));
		result.include("order", content);

		// TODO - the location URI must be absolute... change it to at least not being hard coded
		String location = getLocationFor("/order/" + novoId);
		result.use(Results.http()).setStatusCode(HttpServletResponse.SC_CREATED).addHeader("location", location);
	}

	@Path("/order/{id}")
	@Delete
	public void cancel(Long id) {
		Order order = orders.remove(id);
		if( order == null) {
			result.use(Results.http()).setStatusCode(HttpServletResponse.SC_NOT_FOUND);
		} else {
			order.cancel();
		}
	}

	@Path("/order/{id}")
	@Post
	public void updateOrder(Long id, String content) {
		orders.put(id, new Order(id, content));
		result.include("order", content);
	}
	
	private String getLocationFor(String uri) {
		return "http://" + request.getLocalAddr() + ":" + request.getLocalPort() + request.getContextPath() + uri;
	}
}
