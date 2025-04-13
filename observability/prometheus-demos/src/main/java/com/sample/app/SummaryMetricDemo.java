package com.sample.app;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import io.prometheus.client.Summary;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.CollectorRegistry;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;
import java.util.Random;

public class SummaryMetricDemo {

	// Summary metric for query execution duration
	private static final Summary queryExecutionDuration = Summary.build().name("query_execution_duration_seconds")
			.help("Duration of query execution in seconds").quantile(0.5, 0.05).quantile(0.9, 0.01)
			.quantile(0.99, 0.001).register();

	public static void main(String[] args) throws IOException {
		HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

		server.createContext("/simulate-query", exchange -> {
			Summary.Timer timer = queryExecutionDuration.startTimer();
			try {
				// Simulate query taking 100–500 ms
				Thread.sleep(new Random().nextInt(400) + 100);
				respond(exchange, "Query executed!");
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				timer.observeDuration(); // Record time
			}
		});

		server.createContext("/metrics", new MetricsHandler());

		server.setExecutor(null);
		server.start();
		System.out.println("Server started at http://localhost:8080");
		System.out.println("Hit /simulate-query to simulate DB call and track execution time.");
	}

	private static void respond(HttpExchange exchange, String message) throws IOException {
		exchange.sendResponseHeaders(200, message.length());
		OutputStream os = exchange.getResponseBody();
		os.write(message.getBytes());
		os.close();
	}

	static class MetricsHandler implements HttpHandler {
		@Override
		public void handle(HttpExchange exchange) throws IOException {
			StringWriter writer = new StringWriter();
			Enumeration<io.prometheus.client.Collector.MetricFamilySamples> samples = CollectorRegistry.defaultRegistry
					.metricFamilySamples();
			TextFormat.write004(writer, samples);

			byte[] response = writer.toString().getBytes();
			exchange.getResponseHeaders().set("Content-Type", TextFormat.CONTENT_TYPE_004);
			exchange.sendResponseHeaders(200, response.length);
			try (OutputStream os = exchange.getResponseBody()) {
				os.write(response);
			}
		}
	}
}
