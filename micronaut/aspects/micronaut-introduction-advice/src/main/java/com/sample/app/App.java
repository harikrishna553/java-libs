package com.sample.app;

import com.sample.app.interfaces.AppConfigPropertyExample;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {
			AppConfigPropertyExample appConfigPropertyEx = applicationContext.getBean(AppConfigPropertyExample.class);

			System.out.println(appConfigPropertyEx.runningEnvironment());
			System.out.println(appConfigPropertyEx.applicationName());
		}

	}
}
