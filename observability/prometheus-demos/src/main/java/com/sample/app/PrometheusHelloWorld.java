package com.sample.app;

import io.prometheus.client.exporter.HTTPServer;
import io.prometheus.client.hotspot.DefaultExports;

public class PrometheusHelloWorld {
    public static void main(String[] args) throws Exception {
        // Expose default JVM metrics (optional)
        DefaultExports.initialize();

        // Start Prometheus HTTP server on port 8080
        HTTPServer server = new HTTPServer(8080);

        System.out.println("Prometheus endpoint is available at http://localhost:8080/metrics");

        // Keep the app alive
        while (true) {
            Thread.sleep(1000);
        }
    }
}
