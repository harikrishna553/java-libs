package com.sample.app.signals.metrics;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongUpDownCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;

import java.time.Duration;

/**
 * Demonstrates the use of an {@link LongUpDownCounter} in OpenTelemetry.
 *
 * <p>
 * Unlike a regular counter (which can only increase), an UpDownCounter allows
 * values to both increase and decrease. This makes it well-suited for tracking
 * metrics that fluctuate over time.
 * </p>
 *
 * <p>
 * In this example, we simulate the number of active user sessions in a chat
 * application:
 * <ul>
 * <li>When users join, the counter is incremented.</li>
 * <li>When users leave, the counter is decremented.</li>
 * </ul>
 * </p>
 *
 * <p>
 * The metric values are exported every 2 seconds using the
 * {@link OtlpJsonLoggingMetricExporter}, which prints the data in OTLP JSON
 * format to the application logs. In a real-world system, the same metric could
 * be exported to a monitoring backend such as Prometheus, Grafana, or a cloud
 * APM tool.
 * </p>
 *
 * <h3>Key Concepts</h3>
 * <ul>
 * <li><b>Meter:</b> A factory for creating instruments such as counters and
 * histograms.</li>
 * <li><b>UpDownCounter:</b> A metric that can be increased or decreased, used
 * for tracking values that go up and down.</li>
 * <li><b>PeriodicMetricReader:</b> Collects and exports metrics at fixed
 * intervals.</li>
 * </ul>
 *
 * <h3>Example Run</h3>
 * <ol>
 * <li>Start the application.</li>
 * <li>10 users join → counter increases by 10.</li>
 * <li>5 more users join → counter increases by 5.</li>
 * <li>6 users leave → counter decreases by 6.</li>
 * <li>The exporter prints the updated values to the logs every 2 seconds.</li>
 * </ol>
 */
public class UpDownCounterExample {

	/** A Meter instance for creating instruments in this package. */
	private static final Meter meter;

	/** UpDownCounter for tracking the number of active sessions. */
	private static final LongUpDownCounter activeSessions;

	static {
		// Configure a MeterProvider to export metrics every 2 seconds in JSON format.
		SdkMeterProvider meterProvider = SdkMeterProvider.builder().registerMetricReader(PeriodicMetricReader
				.builder(OtlpJsonLoggingMetricExporter.create()).setInterval(Duration.ofSeconds(2)).build()).build();

		// Register the configured provider as the global OpenTelemetry instance.
		OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();

		// Obtain a Meter for creating metric instruments.
		meter = GlobalOpenTelemetry.getMeter("com.sample.app.signals.metrics");

		// Create an UpDownCounter to track active user sessions.
		activeSessions = meter.upDownCounterBuilder("active.user.sessions")
				.setDescription("Tracks the number of active user sessions in a chat app").setUnit("1").build();
	}

	/**
	 * Main method to simulate user activity and demonstrate how the UpDownCounter
	 * changes over time.
	 *
	 * @param args command-line arguments (not used)
	 * @throws InterruptedException if the thread sleep is interrupted
	 */
	public static void main(String[] args) throws InterruptedException {
		System.out.println("Simulating user sessions...");

		// Simulate 10 users joining
		activeSessions.add(10);
		System.out.println("10 users joined. Active sessions increased.");

		Thread.sleep(2000);

		// Simulate 5 more users joining
		activeSessions.add(5);
		System.out.println("Another 5 users joined. Active sessions increased.");

		Thread.sleep(2000);

		// Simulate 6 users leaving
		activeSessions.add(-6);
		System.out.println("6 users left. Active sessions decreased.");

		// Keep the application alive long enough for metrics to be exported
		Thread.sleep(5000);
	}
}
