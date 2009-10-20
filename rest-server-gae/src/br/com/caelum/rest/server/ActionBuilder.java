package br.com.caelum.rest.server;

import java.util.List;

public interface ActionBuilder {

	<T> T use(Class<T> type);

	List<Action> actions();

}
