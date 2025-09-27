package com.sample.app.signals.spans;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

/**
 * A simple demonstration of intra-process context propagation using
 * OpenTelemetry.
 *
 * <p>
 * This example simulates a very basic e-commerce checkout flow:
 * <ul>
 * <li>A customer places an order (root span).</li>
 * <li>The system validates the order (child span).</li>
 * <li>The system processes the payment (child span).</li>
 * <li>The system confirms the order (child span).</li>
 * </ul>
 *
 * <p>
 * All spans are created inside the same JVM (intra-process). The {@link Scope}
 * API ensures that the current active span automatically becomes the parent of
 * any new spans created within the same thread.
 * </p>
 *
 * <p>
 * Spans are exported as JSON to the console using
 * {@link OtlpJsonLoggingSpanExporter}, but in a real system these would
 * typically be sent to a backend such as Jaeger, Tempo, or Zipkin for
 * visualization.
 * </p>
 */
public class CheckoutApp {

	/**
	 * The tracer is the entry point for creating spans. It is obtained from the
	 * globally registered OpenTelemetry instance.
	 */
	private static final Tracer tracer;

	static {
		// Configure a simple tracer provider with a console exporter
		SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
				.addSpanProcessor(SimpleSpanProcessor.create(OtlpJsonLoggingSpanExporter.create())).build();

		// Register globally so all spans in this process share the same provider
		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

		// Obtain a named tracer (logical component = Checkout flow)
		tracer = GlobalOpenTelemetry.getTracer("CheckoutTracer");
	}

	/**
	 * Main entry point. Starts the checkout flow for a sample order.
	 *
	 * @param args command-line arguments (not used)
	 */
	public static void main(String[] args) {
		placeOrder("CUST-101", "ITEM-555", 2);
	}

	/**
	 * Root operation of the checkout process.
	 *
	 * <p>
	 * This method creates the root span {@code placeOrder}, attaches order-level
	 * attributes, and calls sub-steps. Child spans (validation, payment,
	 * confirmation) will automatically inherit this span as their parent because of
	 * the {@link Scope#makeCurrent()} mechanism.
	 * </p>
	 *
	 * @param customerId ID of the customer placing the order
	 * @param itemId     ID of the item being purchased
	 * @param quantity   Quantity requested
	 */
	static void placeOrder(String customerId, String itemId, int quantity) {
		Span parentSpan = tracer.spanBuilder("placeOrder").setSpanKind(SpanKind.INTERNAL) // internal operation within
																							// service
				.startSpan();

		try (Scope scope = parentSpan.makeCurrent()) {
			parentSpan.setAttribute("customer.id", customerId);
			parentSpan.setAttribute("item.id", itemId);
			parentSpan.setAttribute("quantity", quantity);

			parentSpan.addEvent("Order started");

			// Sub-steps create child spans
			validateOrder(itemId, quantity);
			processPayment(customerId, 199.99);
			confirmOrder(customerId);

			parentSpan.addEvent("Order completed");
			parentSpan.setStatus(StatusCode.OK);
		} catch (Exception e) {
			parentSpan.recordException(e);
			parentSpan.setStatus(StatusCode.ERROR);
		} finally {
			parentSpan.end();
		}
	}

	/**
	 * Step 1: Validate the order.
	 *
	 * <p>
	 * Creates a child span {@code validateOrder}. Adds attributes for item ID and
	 * quantity. Throws an exception if the quantity is invalid.
	 * </p>
	 *
	 * @param itemId   ID of the item being validated
	 * @param quantity Requested quantity
	 */
	static void validateOrder(String itemId, int quantity) {
		Span span = tracer.spanBuilder("validateOrder").startSpan();

		try (Scope scope = span.makeCurrent()) {
			span.setAttribute("item.id", itemId);
			span.setAttribute("quantity", quantity);

			if (quantity <= 0) {
				throw new IllegalArgumentException("Quantity must be greater than 0");
			}

			span.addEvent("Validation successful");
			span.setStatus(StatusCode.OK);
		} catch (Exception e) {
			span.recordException(e);
			span.setStatus(StatusCode.ERROR);
		} finally {
			span.end();
		}
	}

	/**
	 * Step 2: Process the payment.
	 *
	 * <p>
	 * Creates a child span {@code processPayment}. Records payment details as
	 * attributes and simulates a successful payment.
	 * </p>
	 *
	 * @param customerId ID of the customer making the payment
	 * @param amount     Payment amount
	 */
	static void processPayment(String customerId, double amount) {
		Span span = tracer.spanBuilder("processPayment").startSpan();

		try (Scope scope = span.makeCurrent()) {
			span.setAttribute("customer.id", customerId);
			span.setAttribute("payment.amount", amount);

			// Simulate successful payment
			System.out.println("Payment of $" + amount + " received from " + customerId);

			span.addEvent("Payment processed");
			span.setStatus(StatusCode.OK);
		} catch (Exception e) {
			span.recordException(e);
			span.setStatus(StatusCode.ERROR);
		} finally {
			span.end();
		}
	}

	/**
	 * Step 3: Confirm the order.
	 *
	 * <p>
	 * Creates a child span {@code confirmOrder}. Simulates sending a confirmation
	 * to the customer.
	 * </p>
	 *
	 * @param customerId ID of the customer to notify
	 */
	static void confirmOrder(String customerId) {
		Span span = tracer.spanBuilder("confirmOrder").startSpan();

		try (Scope scope = span.makeCurrent()) {
			System.out.println("Order confirmed for customer " + customerId);

			span.addEvent("Order confirmation sent");
			span.setStatus(StatusCode.OK);
		} catch (Exception e) {
			span.recordException(e);
			span.setStatus(StatusCode.ERROR);
		} finally {
			span.end();
		}
	}
}
