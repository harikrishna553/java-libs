package com.sample.app.controller;

import com.sample.app.model.DemoBean;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.Controller;
import io.micronaut.http.annotation.Get;
import io.micronaut.inject.qualifiers.Qualifiers;
import jakarta.inject.Inject;

@Controller
public class HelloWorldController {

	@Inject
	private ApplicationContext applicationContext;

	private String output() {
		DemoBean mySingletonBean1 = applicationContext.getBean(DemoBean.class, Qualifiers.byName("mySingletonBean1"));
		DemoBean mySingletonBean2 = applicationContext.getBean(DemoBean.class, Qualifiers.byName("mySingletonBean2"));
		DemoBean mySingletonBean3 = applicationContext.getBean(DemoBean.class, Qualifiers.byName("mySingletonBean3"));

		StringBuilder builder = new StringBuilder();
		builder.append(mySingletonBean1).append("\n");
		builder.append(mySingletonBean2).append("\n");
		builder.append(mySingletonBean3).append("\n");

		return builder.toString();
	}

	@Get(value = "/hello", produces = MediaType.TEXT_PLAIN)
	public String index() {

		return output();
	}

}