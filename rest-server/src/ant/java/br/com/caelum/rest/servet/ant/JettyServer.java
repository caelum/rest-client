package br.com.caelum.rest.servet.ant;

import java.util.ArrayList;
import java.util.List;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.handler.HandlerCollection;
import org.mortbay.jetty.webapp.WebAppContext;


public class JettyServer extends Task {

	static final int SHUTDOWN = 1;
	private final List<Context> contexts = new ArrayList<Context>();

	private int port;
	private String databasePort;
	private Server server;

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		final JettyServer jetty = new JettyServer();
		jetty.setPort(Integer.parseInt(args[0]));
		jetty.setDatabasePort(args[1]);
		Context ctx = new Context();
		ctx.setContext("/rest-server");
		ctx.setWar(args[2]);
		jetty.addContext(ctx);
		jetty.execute();
	}

	@Override
	public void execute() throws BuildException {
		System.out.println("Starting Jetty on port " + port);
		this.server = new Server(port);

		try {
			Thread.currentThread().setContextClassLoader(this.getClass().getClassLoader());
			List<Handler> lista = new ArrayList<Handler>();
			for (Context ctx : this.contexts) {
				WebAppContext wac = new WebAppContext();
				wac.setAttribute("databasePort", this.databasePort);
				wac.setParentLoaderPriority(true);
				wac.setContextPath(ctx.getContext());
				wac.setWar(ctx.getWar());
				lista.add(wac);
			}
			lista.add(new DefaultHandler());
			HandlerCollection handlers = new HandlerCollection();
			handlers.setHandlers(lista.toArray(new Handler[] {}));
			server.setHandler(handlers);
			server.setStopAtShutdown(true);
			server.start();
			System.out.println("Jetty started");
		} catch (Exception e) {
			e.printStackTrace();
			throw new BuildException(e);
		}
	}

	public void addContext(Context ctx) {
		this.contexts.add(ctx);
	}

	public void setDatabasePort(String databasePort) {
		this.databasePort = databasePort;
	}

}
