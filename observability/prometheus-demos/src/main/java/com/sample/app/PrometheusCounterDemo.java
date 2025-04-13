package com.sample.app;

import java.io.OutputStream;
import java.io.StringWriter;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

import io.prometheus.client.CollectorRegistry;
import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;

public class PrometheusCounterDemo {

	private static final Counter HEALTH_COUNTER = Counter.build().name("health_requests_total")
			.help("Total requests to /health").register();

	private static final Counter USER_PROFILE_COUNTER = Counter.build().name("user_profile_requests_total")
			.help("Total requests to /user-profile").register();

	public static void main(String[] args) throws Exception {
		// DefaultExports.initialize();

		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		server.createContext("/health", exchange -> {
			HEALTH_COUNTER.inc();
			respond(exchange, "Health OK");
		});

		server.createContext("/user-profile", exchange -> {
			USER_PROFILE_COUNTER.inc();
			respond(exchange, "User Profile OK");
		});

		server.createContext("/metrics", exchange -> {
			String response = scrapeMetrics();
			exchange.getResponseHeaders().set("Content-Type", TextFormat.CONTENT_TYPE_004);
			exchange.sendResponseHeaders(200, response.getBytes().length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response.getBytes());
			}
		});

		server.setExecutor(null);
		server.start();
		System.out.println("Server running at http://localhost:8080");
	}

	private static void respond(HttpExchange exchange, String response) throws java.io.IOException {
		exchange.sendResponseHeaders(200, response.length());
		try (OutputStream os = exchange.getResponseBody()) {
			os.write(response.getBytes());
		}
	}

	private static String scrapeMetrics() throws java.io.IOException {
		StringWriter writer = new StringWriter();
		TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());
		return writer.toString();
	}

}
