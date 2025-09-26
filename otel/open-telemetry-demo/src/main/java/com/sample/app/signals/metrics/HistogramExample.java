package com.sample.app.signals.metrics;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongHistogram;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.Aggregation;
import io.opentelemetry.sdk.metrics.InstrumentSelector;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.View;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;

import java.time.Duration;
import java.util.List;
import java.util.Random;

/**
 * <h2>Histogram Example - Tracking Request Latency</h2>
 *
 * <p>
 * This example demonstrates how to use an <b>OpenTelemetry Histogram</b> to
 * capture the distribution of request latencies in a simulated application.
 * Unlike counters or gauges, histograms don’t just record a single value —
 * they group observed values into predefined <i>buckets</i>. This allows you
 * to see the shape of the data (e.g., whether most requests are fast, slow, or
 * spread out).
 * </p>
 *
 * <h3>Use Cases for Histograms</h3>
 * <ul>
 *   <li>Request latency (e.g., how many completed under 100 ms, 500 ms, 1s)</li>
 *   <li>Response payload sizes in KB</li>
 *   <li>Database query execution times</li>
 *   <li>Queue wait times</li>
 * </ul>
 *
 * <h3>How It Works</h3>
 * <ol>
 *   <li>A {@link Histogram} is created via the {@link Meter} API.</li>
 *   <li>Whenever a request completes, we record its latency in milliseconds.</li>
 *   <li>The histogram places the value into the correct bucket.</li>
 *   <li>The {@link PeriodicMetricReader} exports aggregated counts every 2s
 *   using the {@link OtlpJsonLoggingMetricExporter}.</li>
 * </ol>
 *
 * <h3>Why Use Histograms?</h3>
 * <p>
 * Averages can hide performance problems. Two systems may both average 300 ms
 * latency, but one might be consistently ~300 ms while another fluctuates
 * between 50 ms and 2 seconds. Histograms expose this difference by showing
 * distribution across buckets.
 * </p>
 *
 * <h3>Output</h3>
 * <pre>
 * INFO: {
 *   "metrics": [{
 *      "name": "http.server.request.latency",
 *      "histogram": {
 *        "dataPoints": [{
 *          "bucketCounts": ["5", "12", "3"],
 *          "explicitBounds": ["100", "500", "1000"],
 *          "count": "20",
 *          "sum": "4321"
 *        }]
 *      }
 *   }]
 * }
 * </pre>
 */
public class HistogramExample {

    /** Global meter instance used to create metrics. */
    private static final Meter meter;

    static {
        // Configure OpenTelemetry with a periodic reader and JSON exporter
    	SdkMeterProvider meterProvider = SdkMeterProvider.builder()
    		    .registerMetricReader(
    		        PeriodicMetricReader.builder(OtlpJsonLoggingMetricExporter.create())
    		            .setInterval(Duration.ofSeconds(2))
    		            .build())
    		    .registerView(
    		        InstrumentSelector.builder()
    		            .setName("http.server.request.latency") // match instrument name
    		            .build(),
    		        View.builder()
    		            .setAggregation(Aggregation.explicitBucketHistogram(
    		                List.of(100.0, 500.0, 1000.0, 2000.0) // your custom buckets
    		            ))
    		            .build()
    		    )
    		    .build();
        OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();
        meter = GlobalOpenTelemetry.getMeter("com.sample.app.signals.metrics");
    }

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();

        // Create a histogram to track HTTP request latency
        LongHistogram latencyHistogram = meter.histogramBuilder("http.server.request.latency")
                .setDescription("Distribution of HTTP request latencies")
                .setUnit("ms")
                .ofLongs()
                .build();

        System.out.println("Simulating HTTP request latencies...");

        // Simulate 20 requests with random latency
        for (int i = 1; i <= 20; i++) {
            long latency = random.nextInt(5000); // between 50ms and 5000ms
            latencyHistogram.record(latency);
            System.out.printf("Request %d completed in %d ms%n", i, latency);
            Thread.sleep(200); // small pause to simulate requests arriving
        }

        // Keep alive to observe multiple exports
        Thread.sleep(5000);
    }
}
