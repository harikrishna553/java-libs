package com.sample.app.factory;

import com.sample.app.model.DemoBean;

import io.micronaut.context.annotation.Factory;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class BeansFactory {

	@Singleton
	@Named("mySingletonBean1")
	public DemoBean singletonBean1() {
		DemoBean bean = new DemoBean("singletonBean1");
		return bean;
	}

	@Singleton
	@Named("mySingletonBean2")
	public DemoBean singletonBean2() {
		DemoBean bean = new DemoBean("singletonBean2");
		return bean;
	}

	@Singleton
	@Named("mySingletonBean3")
	public DemoBean singletonBean3() {
		DemoBean bean = new DemoBean("singletonBean3");
		return bean;
	}

}
