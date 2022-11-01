package com.sample.app;

import java.util.Random;

import io.micronaut.context.ApplicationContext;
import io.micronaut.core.beans.BeanIntrospection;

public class App {

	public static void main(String[] args) {
		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			final BeanIntrospection<Random> introspection = BeanIntrospection.getIntrospection(Random.class);
			Random random = introspection.instantiate();

			System.out.println(random.nextInt());

		}

	}
}