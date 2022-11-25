package com.sample.app;

import io.micronaut.runtime.Micronaut;

public class App {

	public static void main(String[] args) {
		Micronaut.run(App.class);

		// Use this if you want the beans to be initialized eagerly
		/*
		 * Micronaut.build(args) .eagerInitSingletons(true) .mainClass(App.class)
		 * .start();
		 */
	}
}
