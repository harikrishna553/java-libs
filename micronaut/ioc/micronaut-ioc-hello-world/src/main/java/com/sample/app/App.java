package com.sample.app;

import com.sample.app.util.AnimalUtil;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext context = ApplicationContext.run()) {
			AnimalUtil animalUtil = context.getBean(AnimalUtil.class);
			animalUtil.aboutMe();
		}
	}
}
