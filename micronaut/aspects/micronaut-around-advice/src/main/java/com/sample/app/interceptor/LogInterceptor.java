package com.sample.app.interceptor;

import java.util.Map.Entry;
import java.util.Set;

import com.sample.app.advices.LogAdvice;

import io.micronaut.aop.InterceptorBean;
import io.micronaut.aop.MethodInterceptor;
import io.micronaut.aop.MethodInvocationContext;
import io.micronaut.core.type.MutableArgumentValue;
import jakarta.inject.Singleton;

@Singleton
@InterceptorBean(LogAdvice.class)
public class LogInterceptor implements MethodInterceptor<Object, Object> {

	@Override
	public Object intercept(MethodInvocationContext<Object, Object> context) {

		String methodName = context.getMethodName();
		System.out.println(methodName + " method called with below arguments");

		Set<Entry<String, MutableArgumentValue<?>>> entrySet = context.getParameters().entrySet();
		for (Entry<String, MutableArgumentValue<?>> entry : entrySet) {
			MutableArgumentValue<?> argumentValue = entry.getValue();
			System.out.println("\t" + entry.getKey() + " -> " + argumentValue.getValue());
		}

		return context.proceed();
	}

}
