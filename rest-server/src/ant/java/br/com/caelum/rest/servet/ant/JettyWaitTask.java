package br.com.caelum.rest.servet.ant;

import java.net.Socket;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public class JettyWaitTask extends Task {

	private int port;

	public void setPort(int port) {
		this.port = port;
	}

	public void execute() throws BuildException {

		while (true) {
			try {
				new Socket("localhost", port);
				break;
			} catch (Exception e) {
				try {
					log("Waiting for server to load on port " + port);
					Thread.sleep(10000);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

}
