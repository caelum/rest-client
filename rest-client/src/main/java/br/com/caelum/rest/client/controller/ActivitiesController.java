package br.com.caelum.rest.client.controller;

import br.com.caelum.rest.client.ActivityInfo;
import br.com.caelum.vraptor.Path;
import br.com.caelum.vraptor.Post;
import br.com.caelum.vraptor.Resource;
import br.com.caelum.vraptor.Result;
import br.com.caelum.vraptor.view.Results;

@Resource
public class ActivitiesController {

	private final Result result;
	private final ActivityInfo info;

	public ActivitiesController(ActivityInfo info, Result result) {
		this.info = info;
		this.result = result;
	}

	@Post
	@Path("/cleanUpActivities")
	public void cleanUpActivities() {
		info.cleanUpActivities();
		result.use(Results.nothing());
	}
}
