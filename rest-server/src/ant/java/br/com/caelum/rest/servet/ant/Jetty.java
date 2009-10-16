package br.com.caelum.rest.servet.ant;

import java.util.List;

import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.HandlerCollection;

public class Jetty {

	private final Server server;

	public Jetty(int port) {
		this.server = new Server(port);
		this.server.setStopAtShutdown(true);
	}

	public void stop() throws Exception {
		this.server.stop();
	}

	public void waitForShutdown() throws InterruptedException {
		this.server.join();
	}

	public void start() throws Exception {
		this.server.start();
	}

	public void use(List<Handler> lista) {
		HandlerCollection handlers = new HandlerCollection();
		handlers.setHandlers(lista.toArray(new Handler[] {}));
		server.setHandler(handlers);
	}

}
