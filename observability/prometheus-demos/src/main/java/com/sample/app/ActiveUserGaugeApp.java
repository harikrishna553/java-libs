package com.sample.app;

import com.sun.net.httpserver.HttpServer;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpExchange;
import io.prometheus.client.Gauge;
import io.prometheus.client.exporter.common.TextFormat;
import io.prometheus.client.CollectorRegistry;

import java.io.OutputStream;
import java.io.StringWriter;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.Enumeration;

public class ActiveUserGaugeApp {

    // Gauge metric: can go up/down
    private static final Gauge activeUsersCount = Gauge.build()
            .name("active_users_count")
            .help("Current number of active users")
            .register();

    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Endpoint to simulate user login (increase gauge)
        server.createContext("/login", exchange -> {
            activeUsersCount.inc();
            respond(exchange, "User logged in. Active users: " + activeUsersCount.get());
        });

        // Endpoint to simulate user logout (decrease gauge)
        server.createContext("/logout", exchange -> {
            activeUsersCount.dec();
            respond(exchange, "User logged out. Active users: " + activeUsersCount.get());
        });

        // Metrics endpoint
        server.createContext("/metrics", new MetricsHandler());

        server.setExecutor(null);
        server.start();
        System.out.println("Server started on http://localhost:8080");
        System.out.println("Try hitting /login and /logout, and check /metrics for updates.");
    }

    private static void respond(HttpExchange exchange, String message) throws IOException {
        exchange.sendResponseHeaders(200, message.length());
        OutputStream os = exchange.getResponseBody();
        os.write(message.getBytes());
        os.close();
    }

    // Handler to expose Prometheus metrics
    static class MetricsHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange exchange) throws IOException {
            StringWriter writer = new StringWriter();
            Enumeration<io.prometheus.client.Collector.MetricFamilySamples> samples =
                    CollectorRegistry.defaultRegistry.metricFamilySamples();
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
