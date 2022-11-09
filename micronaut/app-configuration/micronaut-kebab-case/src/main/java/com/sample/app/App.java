package com.sample.app;

import io.micronaut.context.ApplicationContext;
import io.micronaut.context.env.Environment;

public class App {

	public static void main(String[] args) {

		try (ApplicationContext applicationContext = ApplicationContext.run()) {

			Environment environment = applicationContext.getEnvironment();

			String chatServerVersion1 = environment.getProperty("chatServerVersion", String.class,
					"No chat server version found");
			String chatServerVersion2 = environment.getProperty("chat-server-version", String.class,
					"No chat server version found");
			
			String chatServerContactDetails1 = environment.getProperty("chatServer.contactDetails", String.class,
					"No contact details found");
			String chatServerContactDetails2 = environment.getProperty("chat-server.contact-details", String.class,
					"No contact details found");
			
			System.out.println("chatServerVersion1 : " + chatServerVersion1);
			System.out.println("chatServerContactDetails1 : " + chatServerContactDetails1);
			System.out.println("chatServerVersion2 : " + chatServerVersion2);
			System.out.println("chatServerContactDetails2 : " + chatServerContactDetails2);
		}

	}
}