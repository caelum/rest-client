package br.com.caelum.rest.server;

import java.util.HashMap;
import java.util.Map;

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

	public OrderController(Result result, Router route) {
		this.result = result;
		this.route = route;
	}

	private static Map<Long, String> orders = new HashMap<Long, String>();

	@Path("/order/{id}")
	@Get
	public void getOrder(Long id) throws SecurityException, NoSuchMethodException {

		String order = orders.get(id);
		String uri = route.urlFor(OrderController.class, OrderController.class.getMethod("removeOrder", Long.class), new Object[] {id});
		order += "\n<atom:link rel=\"cancel\" href=\"http://localhost:8080/rest-server" + uri + "\" />";
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
		result.use(Results.http()).setStatusCode(HttpServletResponse.SC_CREATED).addHeader("location", "http://localhost:8080/rest-server/order/" + novoId);
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
