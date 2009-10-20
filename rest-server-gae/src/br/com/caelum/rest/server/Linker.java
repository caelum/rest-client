package br.com.caelum.rest.server;

public interface Linker {

	<T extends Restful> String to(T order);

}
