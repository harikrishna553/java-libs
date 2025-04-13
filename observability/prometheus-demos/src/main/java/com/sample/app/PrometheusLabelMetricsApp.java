package com.sample.app;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.hotspot.DefaultExports;
import io.prometheus.client.CollectorRegistry;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.net.InetSocketAddress;
import java.util.Arrays;
import java.util.List;

public class PrometheusLabelMetricsApp {

	// Define counter with both 'endpoint' and 'app_name' labels
	static final Counter appRequestCounter = Counter.build().name("app_total_count")
			.help("Total requests by endpoint and app name").labelNames("endpoint", "app_name").register();

	static final String APP_NAME = "my_app";

	// Supported endpoints
	static final List<String> validEndpoints = Arrays.asList("/health", "/login", "/logout", "/user-profile");

	public static void main(String[] args) throws IOException {
		// Optional: Export default JVM metrics
		// DefaultExports.initialize();

		// Start HTTP server
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		// Handle /metrics endpoint
		server.createContext("/metrics", new MetricsHandler());

		// Handle your 4 sample endpoints
		for (String endpoint : validEndpoints) {
			server.createContext(endpoint, new AppEndpointHandler(endpoint));
		}

		server.setExecutor(null);
		server.start();

		System.out.println("Server started on http://localhost:8080");
		System.out.println("Metrics available at: http://localhost:8080/metrics");
	}

	// Handler for metrics endpoint
	static class MetricsHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			StringWriter writer = new StringWriter();
			TextFormat.write004(writer, CollectorRegistry.defaultRegistry.metricFamilySamples());

			String response = writer.toString();
			exchange.getResponseHeaders().set("Content-Type", TextFormat.CONTENT_TYPE_004);
			exchange.sendResponseHeaders(200, response.length());

			try (OutputStreamWriter os = new OutputStreamWriter(exchange.getResponseBody())) {
				os.write(response);
			}
		}
	}

	// Handler for simulated API endpoints
	static class AppEndpointHandler implements HttpHandler {
		private final String endpoint;

		AppEndpointHandler(String endpoint) {
			this.endpoint = endpoint;
		}

		@Override
		public void handle(HttpExchange exchange) throws IOException {
			// Increment counter with both labels
			appRequestCounter.labels(endpoint, APP_NAME).inc();

			String response = "Hello from " + endpoint;
			exchange.sendResponseHeaders(200, response.length());

			try (OutputStreamWriter os = new OutputStreamWriter(exchange.getResponseBody())) {
				os.write(response);
			}
		}
	}
}
