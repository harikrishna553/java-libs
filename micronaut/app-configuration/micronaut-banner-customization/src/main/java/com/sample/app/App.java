package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.runtime.Micronaut;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = Micronaut.run()) {

		}

	}
}
