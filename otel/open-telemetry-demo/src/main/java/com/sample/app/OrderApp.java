package com.sample.app;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
public class OrderApp {

	private static final Tracer tracer;

	static {
		// Configure OpenTelemetry SDK with a simple console exporter
		SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
				.addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create())).build();

		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

		// Get a Tracer for this class
		tracer = GlobalOpenTelemetry.getTracer("OrderAppTracer");
	}

	public static void main(String[] args) {
		processOrder("ORD-123", 2);
	}

	static void processOrder(String orderId, int quantity) {
		Span span = tracer.spanBuilder("processOrder").startSpan();
		try {
			span.setAttribute("order.id", orderId);
			span.setAttribute("order.quantity", quantity);

			// Make this span the current context
			try (Scope scope = span.makeCurrent()) {
				System.out.println("Processing order " + orderId + " with quantity " + quantity);

				// Now child span will automatically join same trace
				chargePayment(orderId);
			}
		} catch (Exception e) {
			span.recordException(e);
			span.setAttribute("order.success", false);
		} finally {
			span.end();
		}
	}

	static void chargePayment(String orderId) {
		Span span = tracer.spanBuilder("chargePayment").startSpan();
		try (Scope scope = span.makeCurrent()) {
			System.out.println("Charging payment for " + orderId);
			span.setAttribute("payment.status", "success");
		} finally {
			span.end();
		}
	}

}
