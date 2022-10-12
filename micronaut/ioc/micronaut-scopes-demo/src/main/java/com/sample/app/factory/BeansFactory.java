package com.sample.app.factory;

import com.sample.app.model.DemoBean;

import io.micronaut.context.annotation.Context;
import io.micronaut.context.annotation.Factory;
import io.micronaut.context.annotation.Infrastructure;
import io.micronaut.context.annotation.Prototype;
import io.micronaut.runtime.context.scope.Refreshable;
import io.micronaut.runtime.http.scope.RequestScope;
import jakarta.inject.Named;
import jakarta.inject.Singleton;

@Factory
public class BeansFactory {

	@Singleton
	@Named("mySingletonBean")
	public DemoBean singletonBean() {
		DemoBean bean = new DemoBean("singletonBean");
		return bean;
	}

	@Context
	@Named("myContextBean")
	public DemoBean contextBean() {
		DemoBean bean = new DemoBean("contextBean");
		return bean;
	}

	@Prototype
	@Named("myPrototypeBean")
	public DemoBean prototypeBean() {
		DemoBean bean = new DemoBean("prototypeBean");
		return bean;
	}

	@Infrastructure
	@Named("myInfrastructureBean")
	public DemoBean infrastructureBean() {
		DemoBean bean = new DemoBean("infrastructureBean");
		return bean;
	}

	@RequestScope
	@Named("myRequestScopeBean")
	public DemoBean requestScopeBean() {
		DemoBean bean = new DemoBean("requestScopeBean");
		return bean;
	}
	
	@Refreshable
	@Named("myRefreshableScopeBean")
	public DemoBean refreshableScopeBean() {
		DemoBean bean = new DemoBean("refreshableScopeBean");
		return bean;
	}
}
