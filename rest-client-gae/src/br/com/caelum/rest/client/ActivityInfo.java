package br.com.caelum.rest.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@ApplicationScoped
public class ActivityInfo implements Serializable {
	
	private List<Activity> activities;

	public ActivityInfo(HttpSession session) {
		activities = new ArrayList<Activity>();
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
	}

	public void cleanUpActivities() {
		activities = new ArrayList<Activity>();
	}
}
