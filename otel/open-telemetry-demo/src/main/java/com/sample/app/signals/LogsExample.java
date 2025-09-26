package com.sample.app.signals;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.logs.LogRecordBuilder;
import io.opentelemetry.api.logs.Logger;
import io.opentelemetry.exporter.logging.SystemOutLogRecordExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.logs.SdkLoggerProvider;
import io.opentelemetry.sdk.logs.export.BatchLogRecordProcessor;

/**
 * A beginner-friendly example of using OpenTelemetry Logs in Java.
 * <p>
 * OpenTelemetry logs work differently from traditional logging frameworks (like
 * Log4j or java.util.logging). Instead of printing messages directly,
 * OpenTelemetry creates log records, which are then exported using a configured
 * exporter.
 * <p>
 * In this example:
 * <ul>
 * <li>{@link SystemOutLogRecordExporter} is used to export logs to the
 * console.</li>
 * <li>{@link BatchLogRecordProcessor} buffers log records and exports them in
 * batches.</li>
 * <li>{@link SdkLoggerProvider} manages the logger and connects it to the
 * exporter.</li>
 * <li>{@code forceFlush()} is used at the end to ensure logs are exported
 * before the program exits.</li>
 * </ul>
 * <p>
 * Without configuring an exporter + processor, calls to {@code emit()} would
 * not produce visible output.
 */
public class LogsExample {

	/**
	 * Entry point of the application.
	 * <p>
	 * This method demonstrates how to:
	 * <ol>
	 * <li>Configure OpenTelemetry with a log exporter and processor.</li>
	 * <li>Obtain a {@link Logger} from the global OpenTelemetry instance.</li>
	 * <li>Create and emit log records.</li>
	 * <li>Flush logs to make sure they appear before the program ends.</li>
	 * </ol>
	 *
	 * @param args command-line arguments (not used here)
	 */
	public static void main(String[] args) {
		// 1. Configure OpenTelemetry with a SystemOut exporter for logs.
		// BatchLogRecordProcessor collects logs and sends them asynchronously.
		SdkLoggerProvider loggerProvider = SdkLoggerProvider.builder()
				.addLogRecordProcessor(BatchLogRecordProcessor.builder(SystemOutLogRecordExporter.create()).build())
				.build();

		// 2. Register this configuration as the global OpenTelemetry SDK
		OpenTelemetrySdk.builder().setLoggerProvider(loggerProvider).buildAndRegisterGlobal();

		// 3. Obtain a logger instance via the global OpenTelemetry object
		Logger logger = GlobalOpenTelemetry.get().getLogsBridge().get("demo-logs");

		// 4. Create and emit some log records
		LogRecordBuilder record = logger.logRecordBuilder().setTimestamp(Instant.now());
		record.setBody("Application started").emit();

		record.setBody("Processing order...").emit();
		record.setBody("Order completed successfully").emit();

		System.out.println("Logs recorded");

		// 5. Force a flush to ensure all batched logs are exported
		// (important for short-lived apps, otherwise logs may not appear)
		loggerProvider.forceFlush().join(1000, TimeUnit.MILLISECONDS);
	}
}
