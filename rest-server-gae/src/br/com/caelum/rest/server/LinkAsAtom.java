package br.com.caelum.rest.server;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class LinkAsAtom implements Linker {
	private final ActionBuilder builder;

	public LinkAsAtom(ActionBuilder builder) {
		this.builder = builder;
	}


	public <T extends Restful> String to(T object) {
		String content = "";
		for(Action action : object.getActions(builder)) {
			content += "<atom:link rel=\"" + action.getRel() + "\" href=\"" + action.getUri() + "\" />\n";
		}
		return content;
	}

}
