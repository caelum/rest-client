package br.com.caelum.rest.server;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.caelum.vraptor.http.route.Router;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.proxy.MethodInvocation;
import br.com.caelum.vraptor.proxy.Proxifier;

@Component
public class VRaptorActionBuilder implements ActionBuilder {

	private List<Action> actions = new ArrayList<Action>();
	private final Proxifier proxifier;
	private final Router router;
	private final HttpServletRequest request;

	public VRaptorActionBuilder(Proxifier proxifier, Router router, HttpServletRequest request) {
		this.proxifier = proxifier;
		this.router = router;
		this.request = request;
	}

	public <T> T use(final Class<T> type) {
		return proxifier.proxify(type, new MethodInvocation<T>() {
			public Object intercept(T proxy, java.lang.reflect.Method method,
					Object[] args, br.com.caelum.vraptor.proxy.SuperMethod superMethod) {
				String uri = router.urlFor(type, method, args);
				actions.add(new SimpleAction(method.getName(), request, uri));
				return null;
			};
		});
	}

	public List<Action> actions() {
		List<Action> old = actions;
		actions = new ArrayList<Action>();
		return old;
	}

}
