package com.sample.app.signals.metrics;

import java.util.concurrent.TimeUnit;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;

/**
 * <h2>CounterExample - A Beginner Friendly Introduction to OpenTelemetry Metrics</h2>
 *
 * <p>
 * This example demonstrates how to use <b>OpenTelemetry Metrics</b> in a Java
 * application. Specifically, it shows how to:
 * </p>
 *
 * <ul>
 *   <li>Configure the OpenTelemetry SDK to export metrics in <b>JSON format</b> to the console.</li>
 *   <li>Create and use a <b>Counter metric</b> that tracks the number of HTTP requests.</li>
 *   <li>Increment the counter whenever a simulated HTTP request is made.</li>
 *   <li>Automatically export metrics every 2 seconds using a <b>PeriodicMetricReader</b>.</li>
 * </ul>
 *
 * <h3>Key Concepts for Beginners</h3>
 * <ul>
 *   <li><b>Meter:</b> Think of this as a "metrics factory." It is used to create different kinds of metrics like counters, gauges, and histograms.</li>
 *   <li><b>Counter:</b> A metric that only increases. It is commonly used to count events such as "number of requests received."</li>
 *   <li><b>Exporter:</b> Responsible for sending collected metrics to a destination. 
 *       In this example, we use {@link OtlpJsonLoggingMetricExporter}, which prints metrics as JSON logs.</li>
 *   <li><b>PeriodicMetricReader:</b> A helper that collects and exports metrics at regular intervals (every 2 seconds in this example).</li>
 * </ul>
 *
 * <h3>What You’ll See When Running This Program</h3>
 * <p>
 * When executed, the program will:
 * </p>
 * <ol>
 *   <li>Simulate two HTTP requests by calling {@link #httpRequest()} twice.</li>
 *   <li>Increment the counter for each request.</li>
 *   <li>Print logs confirming the requests were handled.</li>
 *   <li>Export the metrics to the console in <b>OpenTelemetry JSON format</b>, showing the total count of requests.</li>
 * </ol>
 *
 * <h3>Why This Matters</h3>
 * <p>
 * Metrics are a core part of observability. By collecting counts of HTTP requests (or any other business event), 
 * you gain visibility into how your application behaves in real time. 
 * This simple example lays the foundation for building production-grade monitoring systems.
 * </p>
 *
 * <h3>Tip:</h3>
 * <p>
 * In a real-world Spring Boot or microservice application, you would expose these metrics 
 * to a monitoring backend such as Prometheus, Grafana, or another observability tool, instead of just logging them.
 * </p>
 */
public class CounterExample {

    /** A Meter creates and manages metrics in OpenTelemetry. */
    private static final Meter meter;

    /** Counter to track the number of HTTP requests handled. */
    private static final LongCounter requestCounter;

    static {
        // Configure the OpenTelemetry SDK with an exporter and reader.
        // The exporter will log metrics as JSON to the console.
        // The reader ensures metrics are exported every 2 seconds.
        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
                .registerMetricReader(
                        PeriodicMetricReader.builder(OtlpJsonLoggingMetricExporter.create())
                                .setInterval(java.time.Duration.ofSeconds(2))
                                .build()
                )
                .build();

        // Register this configuration as the global OpenTelemetry instance.
        OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();

        // Get a meter for this package/class.
        meter = GlobalOpenTelemetry.getMeter("com.sample.app.signals.metrics");

        // Build a counter metric to track HTTP requests.
        requestCounter = meter.counterBuilder("http.requests")
                .setDescription("Counts incoming HTTP requests")
                .setUnit("1")
                .build();
    }

    /**
     * Main entry point of the application.
     * <p>
     * Simulates two HTTP requests, increments the counter, and keeps the program alive
     * long enough for the metrics to be exported.
     * </p>
     *
     * @param args not used
     * @throws InterruptedException if the sleep is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        // Simulate a few requests
        httpRequest();
        httpRequest();

        System.out.println("Two HTTP requests processed... wait to see metrics in OTEL log format.");

        // Keep the app alive long enough for metrics to be exported
        TimeUnit.SECONDS.sleep(5);
    }

    /**
     * Simulates handling of a single HTTP request.
     * <p>
     * Each time this method is called, the request counter is incremented by 1.
     * </p>
     */
    private static void httpRequest() {
        requestCounter.add(1);
		System.out.println("HTTP request handled, counter incremented.");
	}
}
