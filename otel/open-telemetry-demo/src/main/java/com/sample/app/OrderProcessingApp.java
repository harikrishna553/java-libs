package com.sample.app;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;

public class OrderProcessingApp {

	private static final Tracer tracer;

	static {
		// Configure OpenTelemetry SDK with a simple console exporter
		SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
				.addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create())).build();

		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

		tracer = GlobalOpenTelemetry.getTracer("OrderProcessingTracer");
	}

	public static void main(String[] args) {
		// Process a successful order
		processOrder("ORD-123", 2);
	}

	static void processOrder(String orderId, int quantity) {
		Span span = tracer.spanBuilder("processOrder").startSpan();
		try (Scope scope = span.makeCurrent()) {
			span.setAttribute("order.id", orderId);
			span.setAttribute("order.quantity", quantity);

			System.out.println("Starting order processing for: " + orderId);

			validateOrder(orderId, quantity);
			chargePayment(orderId, quantity);
			sendNotification(orderId);

			span.setAttribute("order.success", true);
		} catch (Exception e) {
			span.recordException(e);
			span.setAttribute("order.success", false);
			System.err.println("Failed to process order " + orderId + ": " + e.getMessage());
		} finally {
			span.end();
		}
	}

	static void validateOrder(String orderId, int quantity) {
		Span span = tracer.spanBuilder("validateOrder").startSpan();
		try (Scope scope = span.makeCurrent()) {
			if (quantity <= 0) {
				throw new IllegalArgumentException("Quantity must be greater than 0");
			}
			System.out.println("Order " + orderId + " validated successfully");
			span.setAttribute("validation.status", "passed");
		} catch (Exception e) {
			span.recordException(e);
			span.setAttribute("validation.status", "failed");
			throw e;
		} finally {
			span.end();
		}
	}

	static void chargePayment(String orderId, int quantity) {
		Span span = tracer.spanBuilder("chargePayment").startSpan();
		try (Scope scope = span.makeCurrent()) {
			System.out.println("Charging payment for order " + orderId);

			// Simulate payment success/failure
			if ("ORD-123".equals(orderId)) {
				span.setAttribute("payment.status", "success");
				System.out.println("Payment successful for " + orderId);
			} else {
				throw new RuntimeException("Payment gateway declined");
			}
		} catch (Exception e) {
			span.recordException(e);
			span.setAttribute("payment.status", "failed");
			throw e;
		} finally {
			span.end();
		}
	}

	static void sendNotification(String orderId) {
		Span span = tracer.spanBuilder("sendNotification").startSpan();
		try (Scope scope = span.makeCurrent()) {
			System.out.println("Sending confirmation email for order " + orderId);
			span.setAttribute("notification.sent", true);
		} finally {
			span.end();
		}
	}
}
