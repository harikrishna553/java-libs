package com.sample.app;

import java.io.IOException;
import java.util.Random;

import io.prometheus.client.Histogram;
import io.prometheus.client.exporter.HTTPServer;

public class HistogramCustomBucketDemo {

	// Define the Histogram metric
	static final Histogram requestDuration = Histogram.build().name("http_request_duration_seconds")
			.help("Duration of HTTP requests in seconds").buckets(0.1, 0.2, 0.5, 1, 2, 5).register();

	public static void main(String[] args) throws IOException {
		// Start Prometheus metrics HTTP server on port 8080
		HTTPServer server = new HTTPServer(8080);

		// Optional: Expose JVM metrics (GC, memory, threads)
		// DefaultExports.initialize();

		// Simulate continuous request processing
		HistogramCustomBucketDemo example = new HistogramCustomBucketDemo();
		Random rand = new Random();

		while (true) {
			example.simulateRequest(rand.nextInt(5000) + 100);
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
