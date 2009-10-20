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

	public OrderController(Result result, Router route, HttpServletRequest request) {
		this.result = result;
		this.route = route;
		this.request = request;
	}

	private static Map<Long, String> orders = new HashMap<Long, String>();

	@Path("/order/{id}")
	@Get
	public void getOrder(Long id) throws SecurityException, NoSuchMethodException {

		String order = orders.get(id);
		String uri = route.urlFor(OrderController.class, OrderController.class.getMethod("removeOrder", Long.class), new Object[] {id});
		String location = getLocationFor(uri);
		order += "\n<atom:link rel=\"cancel\" href=\"" + location + "\" />";
		result.include("order", order);
		if(order == null) {
			result.use(Results.http()).setStatusCode(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Path("/order")
	@Post
	public void createOrder(String order) {
		long novoId = orders.size() + 1;
		orders.put(novoId, order);
		result.include("order", order);

		// TODO - the location URI must be absolute... change it to at least not being hard coded
		String location = getLocationFor("/order/" + novoId);
		result.use(Results.http()).setStatusCode(HttpServletResponse.SC_CREATED).addHeader("location", location);
	}

	private String getLocationFor(String val) {
		return "http://" + request.getLocalName() + ":" + request.getLocalPort() + request.getContextPath() + val;
	}

	@Path("/order/{id}")
	@Delete
	public void removeOrder(Long id) {
		String removedOrder = orders.remove(id);
		if( removedOrder == null) {
			result.use(Results.http()).setStatusCode(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	@Path("/order/{id}")
	@Post
	public void updateOrder(Long id, String order) {
		orders.put(id, order);
		result.include("order", order);
	}
}
