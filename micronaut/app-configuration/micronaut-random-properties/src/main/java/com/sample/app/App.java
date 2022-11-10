package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Environment environment = applicationContext.getEnvironment();

			Integer randomPort = environment.getProperty("randomPort", Integer.class, 0);

			Integer randomInt1 = environment.getProperty("randomInt1", Integer.class, 0);
			Integer randomInt2 = environment.getProperty("randomInt2", Integer.class, 0);
			Long randomLong = environment.getProperty("randomLong", Long.class, 0l);
			Float randomFloat = environment.getProperty("randomFloat", Float.class, 0.0f);

			String randomShortUUID = environment.getProperty("randomShortUUID", String.class, "null");
			String randomUUID1 = environment.getProperty("randomUUID1", String.class, "null");
			String randomUUID2 = environment.getProperty("randomUUID2", String.class, "null");

			Integer randomIntRange1 = environment.getProperty("randomIntRange1", Integer.class, 0);
			Integer randomIntRange2 = environment.getProperty("randomIntRange2", Integer.class, 0);

			System.out.println("randomPort : " + randomPort);
			System.out.println("randomInt1 : " + randomInt1);
			System.out.println("randomInt2 : " + randomInt2);
			System.out.println("randomLong : " + randomLong);
			System.out.println("randomFloat : " + randomFloat);
			System.out.println("randomShortUUID : " + randomShortUUID);
			System.out.println("randomUUID1 : " + randomUUID1);
			System.out.println("randomUUID2 : " + randomUUID2);
			System.out.println("randomIntRange1 : " + randomIntRange1);
			System.out.println("randomIntRange2 : " + randomIntRange2);
		}

	}
}