package com.sample.app;

import io.prometheus.client.Counter;
import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

import java.util.Scanner;

public class PrometheusCounterApp {

	// Define a counter for /health
	static final Counter healthRequests = Counter.build().name("health_endpoint_requests_total")
			.help("Total requests to the /health simulated endpoint").register();

	// Define a counter for /user-profile
	static final Counter userProfileRequests = Counter.build().name("user_profile_endpoint_requests_total")
			.help("Total requests to the /user-profile simulated endpoint").register();

	public static void main(String[] args) throws Exception {
		// Expose default JVM metrics
		DefaultExports.initialize();

		// Start Prometheus HTTP server on port 8080
		HTTPServer server = new HTTPServer(8080);
		System.out.println("🚀 Prometheus metrics available at: http://localhost:8080/metrics");

		// Simulate endpoint accesses using simple menu
		Scanner scanner = new Scanner(System.in);
		while (true) {
			System.out.println("\nChoose an option:");
			System.out.println("1. Call /health");
			System.out.println("2. Call /user-profile");
			System.out.println("3. Exit");
			System.out.print("Your choice: ");

			int choice = scanner.nextInt();
			switch (choice) {
			case 1:
				healthRequests.inc();
				System.out.println("/health endpoint simulated.");
				break;
			case 2:
				userProfileRequests.inc();
				System.out.println("/user-profile endpoint simulated.");
				break;
			case 3:
				System.out.println("👋 Exiting...");
				server.stop();
				System.exit(0);
				break;
			default:
				System.out.println("Invalid option.");
			}
		}
	}
}
