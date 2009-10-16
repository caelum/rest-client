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
	private final List<Context> contexts = new ArrayList<Context>();

	private int port;
	private String databasePort;
	private Jetty jetty;

	public void setPort(int port) {
		this.port = port;
	}

	public static void main(String[] args) throws Exception {
		final JettyStartTask task = new JettyStartTask();
		task.setPort(Integer.parseInt(args[0]));
		JOptionPane.showMessageDialog(null, "farofa");
		PrintStream out = new PrintStream(extractLogName(args, task.port));
		System.setOut(out);
		System.setErr(out);
		System.out.println(">> Running on port " + task.port);
		task.setDatabasePort(args[1]);
		Context ctx = new Context();
		ctx.setContext("/rest-server");
		ctx.setWar(args[2]);
		System.out.println("Using file" + args[2]);
		task.addContext(ctx);
		Thread t = new Thread() {
			@Override
			public void run() {
				task.execute();
			}
		};
		t.start();
		ServerSocket ss = new ServerSocket(task.port + 100);
		System.out.println("Waiting for socket shutdown");
		System.out.flush();
		while (true) {
			Socket s = ss.accept();
			System.out.println("Accepted a new socket");
			ObjectInputStream ois = new ObjectInputStream(s.getInputStream());
			int val = ois.readInt();
			System.out.println("Read " + val);
			s.close();
			if (val == SHUTDOWN) {
				break;
			}
		}
		System.out.println("Stopping server at port " + task.port);
		task.jetty.stop();
		System.out.println("Stopped server at port " + task.port);
		System.out.flush();
		out.close();
		System.exit(0);
	}

	private static String get(String key, String[] args) {
		for(String s : args) {
			if(s.startsWith("-" + key + "=")) {
				return s.substring(2+ key.length());
			}
		}
		throw new RuntimeException("Unable to find arg " + key);
	}

	private static File extractLogName(String[] args, int port) {
		File base = new File(".");
		for (int i = 0; i < args.length; i++) {
			String arg = args[i];
			if(arg.startsWith("-logDir=")) {
				base = new File(arg.substring(8));
			}
		}
		base.mkdirs();
		File logFile = new File(base, "jetty_" + port + ".log");
		System.out.println("Writing to " + logFile.getAbsolutePath());
		return logFile;
	}

	@Override
	public void execute() throws BuildException {

		this.jetty = new Jetty(port);
//		System.setProperty("extraConfiguration", "hibernate.mysql.xml");

		try {
			Thread.currentThread().setContextClassLoader(
					this.getClass().getClassLoader());
			List<Handler> lista = new ArrayList<Handler>();
			for (Context ctx : this.contexts) {
				WebAppContext wac = new WebAppContext();
				wac.setParentLoaderPriority(true);
				wac.setContextPath(ctx.getContext());
				wac.setWar(ctx.getWar());
				lista.add(wac);
			}
			lista.add(new DefaultHandler());
			jetty.use(lista);
			this.jetty.start();
			System.out.println("Server started @ " + port + " with database port " + this.databasePort);
			System.out.println("Always waiting for shutdown");
			this.jetty.waitForShutdown();
		} catch (Exception e) {
			e.printStackTrace(System.out);
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
