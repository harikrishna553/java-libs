package com.sample.app.signals.metrics;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.ObservableLongGauge;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.time.Duration;

/**
 * <h2>Gauge Example - Tracking JVM Heap Memory Usage</h2>
 *
 * <p>
 * This example demonstrates how to use an <b>OpenTelemetry Gauge</b> to monitor
 * the current state of a value at runtime. Unlike counters (which only
 * increase) or up-down counters (which can increase and decrease), a gauge
 * captures the <i>point-in-time measurement</i> of a metric.
 * </p>
 *
 * <p>
 * In this example, we monitor the JVM heap memory usage. Each time OpenTelemetry
 * collects metrics, it executes a callback function to fetch the current memory
 * usage, converts it into megabytes, and records it as the gauge value.
 * </p>
 *
 * <h3>Use Cases for Gauges</h3>
 * <ul>
 *   <li>Current CPU utilization percentage</li>
 *   <li>Heap memory usage in MB</li>
 *   <li>Temperature of a server rack</li>
 *   <li>Queue size in a messaging system</li>
 * </ul>
 *
 * <h3>How It Works</h3>
 * <ol>
 *   <li>An {@link ObservableLongGauge} is created using the
 *   {@link Meter#gaugeBuilder(String)} API.</li>
 *   <li>The gauge registers a callback that is invoked periodically.</li>
 *   <li>In the callback, we fetch JVM heap memory usage via
 *   {@link MemoryMXBean}, convert it to MB, and record the value.</li>
 *   <li>The {@link PeriodicMetricReader} exports the gauge value every 2 seconds
 *   using the {@link OtlpJsonLoggingMetricExporter}.</li>
 *   <li>The metric is logged in pretty JSON format for inspection.</li>
 * </ol>
 *
 * <h3>Output</h3>
 * <p>
 * On the console, you’ll see log messages like:
 * </p>
 * <pre>
 * Observed heap usage: 45 MB
 * </pre>
 * <p>
 * Alongside JSON-formatted OpenTelemetry metric data.
 * </p>
 *
 * <h3>Key Point</h3>
 * <p>
 * A gauge reports <i>"what the value is right now"</i>, not how it changed over
 * time. This makes it ideal for tracking fluctuating system states.
 * </p>
 */
public class GaugeExample {

    /** Global meter instance used to create metrics. */
    private static final Meter meter;

    static {
        // Configure OpenTelemetry with a periodic reader and JSON exporter
        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
                .registerMetricReader(
                        PeriodicMetricReader.builder(OtlpJsonLoggingMetricExporter.create())
                                .setInterval(Duration.ofSeconds(2)) // collect every 2s
                                .build())
                .build();

        OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();
        meter = GlobalOpenTelemetry.getMeter("com.sample.app.signals.metrics");
    }

    /**
     * Application entry point.
     *
     * <p>
     * Creates an observable gauge that measures current JVM heap memory usage in
     * megabytes. The gauge is sampled automatically by OpenTelemetry every 2
     * seconds until the program terminates.
     * </p>
     *
     * @param args Command-line arguments (not used).
     * @throws InterruptedException if the main thread is interrupted while
     *                              sleeping.
     */
    public static void main(String[] args) throws InterruptedException {
        MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

        // Build a gauge to observe current heap usage
        ObservableLongGauge heapGauge = meter.gaugeBuilder("jvm.memory.heap.used")
                .setDescription("Current heap memory used in MB")
                .setUnit("MB")
                .ofLongs()
                .buildWithCallback(measurement -> {
                    long heapUsedBytes = memoryBean.getHeapMemoryUsage().getUsed();
                    long heapUsedMB = heapUsedBytes / (1024 * 1024);
                    measurement.record(heapUsedMB);
                    System.out.println("Observed heap usage: " + heapUsedMB + " MB");
                });

        // Keep alive to observe multiple exports
        Thread.sleep(10000);

        // Clean up and unregister the gauge
        heapGauge.close();
    }
}
