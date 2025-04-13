package com.sample.app;

import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.io.IOException;
import java.util.Random;

public class HistogramDefaultBucketDemo {

	// Define the Histogram metric
	static final Histogram requestDuration = Histogram.build().name("http_request_duration_seconds")
			.help("Duration of HTTP requests in seconds").register();

	public static void main(String[] args) throws IOException {
		// Start Prometheus metrics HTTP server on port 8080
		HTTPServer server = new HTTPServer(8080);

		// Optional: Expose JVM metrics (GC, memory, threads)
		// DefaultExports.initialize();

		// Simulate continuous request processing
		HistogramDefaultBucketDemo example = new HistogramDefaultBucketDemo();
		Random rand = new Random();

		while (true) {
			example.simulateRequest(rand.nextInt(400) + 100); // simulate 100–500 ms processing
			try {
				Thread.sleep(1000); // simulate 1 request per second
			} catch (InterruptedException e) {
				Thread.currentThread().interrupt();
			}
		}
	}

	// Simulates handling a request and records duration in Histogram
	public void simulateRequest(long processingTimeMillis) {
		Histogram.Timer timer = requestDuration.startTimer();
		try {
			Thread.sleep(processingTimeMillis); // simulate processing time
			System.out.println("Processed request in " + processingTimeMillis + " ms");
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		} finally {
			timer.observeDuration(); // records time in seconds
		}
	}
}
