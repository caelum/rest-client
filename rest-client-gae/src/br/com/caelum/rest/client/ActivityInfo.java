package br.com.caelum.rest.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class ActivityInfo implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<Activity> activities;

	public ActivityInfo() {
		activities = new ArrayList<Activity>();
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
	}
	
	public List<Activity> getActivities() {
		return activities;
	}

	public void cleanUpActivities() {
		activities = new ArrayList<Activity>();
	}
}
