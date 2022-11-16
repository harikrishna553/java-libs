package com.sample.app;

import com.sample.app.components.ArithmeticUtil;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			ArithmeticUtil arithmeticUtil = applicationContext.getBean(ArithmeticUtil.class);
			arithmeticUtil.add(10, 20);
		}

	}
}
