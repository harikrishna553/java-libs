package com.sample.app.signals.metrics;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.ObservableLongCounter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;

/**
 * <h2>AsynchronousCounterExample - A Beginner Friendly Introduction</h2>
 *
 * <p>
 * This example demonstrates how to use an <b>Asynchronous Counter</b> in
 * OpenTelemetry:
 * </p>
 *
 * <ul>
 * <li>Configure the SDK to export metrics in JSON format to the console.</li>
 * <li>Register an asynchronous counter that reports the <b>total rows read from
 * a DB</b>.</li>
 * <li>OTEL calls the callback every 2 seconds to collect the latest value.</li>
 * </ul>
 */
public class AsynchronousCounterExample {

	/** Fake "rows read" tracker to simulate database reads. */
	private static final AtomicLong rowsRead = new AtomicLong(0);

	static {
		// Configure SDK with JSON exporter and periodic metric reader
		SdkMeterProvider meterProvider = SdkMeterProvider.builder().registerMetricReader(PeriodicMetricReader
				.builder(OtlpJsonLoggingMetricExporter.create()).setInterval(java.time.Duration.ofSeconds(2)).build())
				.build();

		OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();

		Meter meter = GlobalOpenTelemetry.getMeter("com.sample.app.signals.metrics");

		// Create an asynchronous counter for rows read
		ObservableLongCounter observableLongCounter = meter.counterBuilder("db.rows.read")
				.setDescription("Total number of rows read from database").setUnit("1")
				.buildWithCallback(measurement -> {
					long current = rowsRead.get();
					measurement.record(current);
				});
	}

	public static void main(String[] args) throws InterruptedException {
		// Simulate database queries
		simulateQuery();
		simulateQuery();

		System.out.println("Two DB queries executed. OTEL will report metrics periodically...");

		// Keep app alive for metrics export
		TimeUnit.SECONDS.sleep(6);

		// Simulate another query after some delay
		simulateQuery();

		System.out.println("One more DB query executed... wait for next OTEL export.");

		TimeUnit.SECONDS.sleep(6);
	}

	/** Simulates a database query and increases the row count. */
	private static void simulateQuery() {
		long rows = (long) (Math.random() * 100); // fake rows read
		rowsRead.addAndGet(rows);
		System.out.println(rows + " rows read from DB.");
	}
}
