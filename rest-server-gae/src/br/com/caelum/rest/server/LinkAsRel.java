package br.com.caelum.rest.server;

import br.com.caelum.vraptor.ioc.Component;

@Component
public class LinkAsRel implements Linker{
	
	private final ActionBuilder builder;

	public LinkAsRel(ActionBuilder builder) {
		this.builder = builder;
	}

	public <T extends Restful> String to(T object) {
		String content = "";
		for(Action action : object.getActions(builder)) {
			content += "<" + action.getRel() + ">" + action.getUri() + "</" + action.getRel() + ">\n";
		}
		return content;
	}


}
