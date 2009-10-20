package br.com.caelum.rest.server;

import java.util.List;

public interface Restful {

	public List<Action> getActions(ActionBuilder builder);

}
