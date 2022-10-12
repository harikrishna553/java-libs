package com.sample.app.controller;

import com.sample.app.model.DemoBean;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.inject.qualifiers.Qualifiers;
import io.micronaut.runtime.context.scope.refresh.RefreshEvent;
import jakarta.inject.Inject;

@Controller
public class HelloWorldController {

	@Inject
	private ApplicationContext applicationContext;

	private String output() {
		DemoBean mySingletonBean = applicationContext.getBean(DemoBean.class, Qualifiers.byName("mySingletonBean"));
		DemoBean myContextBean = applicationContext.getBean(DemoBean.class, Qualifiers.byName("myContextBean"));
		DemoBean myPrototypeBean = applicationContext.getBean(DemoBean.class, Qualifiers.byName("myPrototypeBean"));
		DemoBean myInfrastructureBean = applicationContext.getBean(DemoBean.class,
				Qualifiers.byName("myInfrastructureBean"));
		DemoBean myRequestScopeBean = applicationContext.getBean(DemoBean.class,
				Qualifiers.byName("myRequestScopeBean"));
		DemoBean myRefreshableScopeBean = applicationContext.getBean(DemoBean.class,
				Qualifiers.byName("myRefreshableScopeBean"));

		StringBuilder builder = new StringBuilder();
		builder.append(mySingletonBean).append("\n");
		builder.append(myContextBean).append("\n");
		builder.append(myPrototypeBean).append("\n");
		builder.append(myInfrastructureBean).append("\n");
		builder.append(myRequestScopeBean).append("\n");
		builder.append(myRefreshableScopeBean).append("\n");

		return builder.toString();
	}

	@Get(value = "/hello", produces = MediaType.TEXT_PLAIN)
	public String index() {

		return output();
	}

	@Get(value = "/publish-refresh-event", produces = MediaType.TEXT_PLAIN)
	public String refresh() {

		applicationContext.publishEvent(new RefreshEvent());

		return output();
	}
}