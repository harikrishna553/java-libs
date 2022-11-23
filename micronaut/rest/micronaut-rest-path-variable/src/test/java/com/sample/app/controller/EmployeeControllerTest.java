package com.sample.app.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.sample.app.model.Employee;

import io.micronaut.context.ApplicationContext;
import io.micronaut.http.client.HttpClient;
import io.micronaut.runtime.server.EmbeddedServer;

public class EmployeeControllerTest {

	private static EmbeddedServer server;
	private static HttpClient client;

	@BeforeAll
	public static void setupServer() {
		server = ApplicationContext.run(EmbeddedServer.class);
		client = server.getApplicationContext().createBean(HttpClient.class, server.getURL());
	}

	@AfterAll
	public static void stopServer() {
		if (server != null) {
			server.stop();
		}
		if (client != null) {
			client.stop();
		}
	}

	@Test
	public void testIssue() {
		Employee emp = client.toBlocking().retrieve("/employees/by-id/1", Employee.class);

		assertEquals(1, emp.getId());
		assertEquals("Sunil", emp.getName());
		assertEquals(23, emp.getAge());
	}

}
