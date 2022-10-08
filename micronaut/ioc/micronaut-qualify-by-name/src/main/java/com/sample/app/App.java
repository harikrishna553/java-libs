package com.sample.app;

import com.sample.app.util.DogService;

import io.micronaut.context.ApplicationContext;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext context = ApplicationContext.run()) {
			DogService animalUtil = context.getBean(DogService.class);
			animalUtil.aboutMe();
		}
	}
}
