package br.com.caelum.rest.client;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import br.com.caelum.vraptor.ioc.Component;
import br.com.caelum.vraptor.ioc.SessionScoped;

@Component
@SessionScoped
public class ActivityInfo {
	private final HttpSession session;
	private final List<Activity> activities;

	public ActivityInfo(HttpSession session) {
		this.session = session;
		activities = new ArrayList<Activity>();
	}

	public void addActivity(Activity activity) {
		activities.add(activity);
		session.setAttribute("activities", activities);
	}
}
