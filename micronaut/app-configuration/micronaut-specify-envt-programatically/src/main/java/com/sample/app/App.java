package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.ApplicationContextBuilder;
import io.micronaut.context.ApplicationContextConfigurer;
import io.micronaut.context.annotation.ContextConfigurer;
import io.micronaut.context.env.Environment;
import io.micronaut.core.annotation.NonNull;
import io.micronaut.runtime.Micronaut;

public class App {

	@ContextConfigurer
	public static class DefaultEnvironmentConfigurer implements ApplicationContextConfigurer {
		@Override
		public void configure(@NonNull ApplicationContextBuilder builder) {
			builder.defaultEnvironments("dev", "qa");
		}
	}

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = Micronaut.run(App.class, args)) {

			Environment environment = applicationContext.getEnvironment();

			String a1 = environment.getProperty("a1", String.class, "a1");
			String a2 = environment.getProperty("a2", String.class, "a2");

			System.out.println("a1 : " + a1);
			System.out.println("a2 : " + a2);
		}

	}
}
