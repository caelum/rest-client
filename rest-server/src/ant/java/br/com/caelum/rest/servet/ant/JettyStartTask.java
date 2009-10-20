package br.com.caelum.rest.servet.ant;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;
import org.mortbay.jetty.Handler;
import org.mortbay.jetty.handler.DefaultHandler;
import org.mortbay.jetty.webapp.WebAppContext;

public class JettyStartTask extends Task {

	static final int SHUTDOWN = 1;

	private Jetty jetty;

	public static void main(String[] args) throws Exception {
		final JettyStartTask task = new JettyStartTask();
		Context ctx = new Context();
		ctx.setContext("/rest-server");
		ctx.setWar(".");
		System.out.println("Using file" + args[2]);
		task.addContext(ctx);
		Thread t = new Thread() {
			@Override
			public void run() {
				task.execute(8080);
			}
		};
		t.start();
		ServerSocket ss = new ServerSocket(task.port + 100);
		System.out.println("Press any key to shutdown...");
		System.in.read();
		task.jetty.stop();
		out.close();
		System.exit(0);
	}

	public void execute(int port) throws Exception {

		this.jetty = new Jetty(port);

			Thread.currentThread().setContextClassLoader(
					this.getClass().getClassLoader());
			List<Handler> lista = new ArrayList<Handler>();
			WebAppContext wac = new WebAppContext();
			wac.setParentLoaderPriority(true);
			wac.setContextPath("/rest-server");
			wac.setWar(".");
			lista.add(wac);
			lista.add(new DefaultHandler());
			jetty.use(lista);
			this.jetty.start();
			System.out.println("Server started @ " + port);
			this.jetty.waitForShutdown();
	}

}
