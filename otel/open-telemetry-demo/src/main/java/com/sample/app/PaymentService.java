package com.sample.app;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.api.GlobalOpenTelemetry;

public class PaymentService {

	private static final Tracer tracer;

	static {
		// Configure OpenTelemetry SDK with a simple console exporter
		SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
				.addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create())).build();

		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

		// Get a Tracer for this class
		tracer = GlobalOpenTelemetry.getTracer("OrderAppTracer");
	}

	public static void processPayment(String orderId) {
		Span span = tracer.spanBuilder("processPayment").startSpan();

		try {
			span.setAttribute("order.id", orderId);
			// business logic
			System.out.println("Processing order: " + orderId);
		} finally {
			span.end();
		}
	}

	public static void main(String[] args) {
		processPayment("12345");
	}
}
