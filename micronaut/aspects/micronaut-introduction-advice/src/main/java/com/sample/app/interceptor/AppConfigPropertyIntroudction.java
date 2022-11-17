package com.sample.app.interceptor;

import java.util.Optional;

import com.sample.app.advices.AppConfigProperty;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.Nullable;
import jakarta.inject.Singleton;

@Singleton
@InterceptorBean(AppConfigProperty.class)
public class AppConfigPropertyIntroudction implements MethodInterceptor<Object, Object> { 
	
	private Environment environment;
	
	public AppConfigPropertyIntroudction(Environment environment) {
		this.environment = environment;
	}

	@Nullable
	@Override
	public Object intercept(MethodInvocationContext<Object, Object> context) {
		// As this annotation has only one property, this is sufficient
		 Optional<Object> valueOpt = context.getValue(AppConfigProperty.class, context.getReturnType().getType());
		 
		 if(valueOpt.isEmpty()) {
			 return null;
		 }
		 
		 Object obj = valueOpt.get();
		 String value = obj.toString();
		 return environment.getProperty(value, String.class, "no_property configured");
	}
}