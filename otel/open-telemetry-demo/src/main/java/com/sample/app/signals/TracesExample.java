package com.sample.app.signals;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.exporter.logging.LoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

/**
 * A simple example that demonstrates how to generate and export traces using
 * the OpenTelemetry Java SDK.
 *
 * <p>
 * This example uses the {@link LoggingSpanExporter}, which writes trace data
 * (spans) to the application console. In a real-world application, you would
 * typically configure an exporter that sends spans to a backend such as Jaeger,
 * Zipkin, or an observability platform.
 *
 * <h2>How it works</h2>
 * <ul>
 * <li>A {@link SdkTracerProvider} is configured with a
 * {@link SimpleSpanProcessor} that uses the {@link LoggingSpanExporter}.</li>
 * <li>The provider is registered globally with {@link OpenTelemetrySdk} so that
 * instrumentation code can access it anywhere in the app.</li>
 * <li>A {@link Tracer} is obtained to create spans.</li>
 * <li>We create a parent span ("process-order") and two child spans
 * ("validate-order" and "process-payment").</li>
 * <li>Each span is started, some work is simulated with a print statement, and
 * then the span is ended.</li>
 * </ul>
 *
 * <p>
 * When you run this program, you will see the spans printed to the console with
 * their names, timings, and relationships.
 */
public class TracesExample {

	/**
	 * The tracer used to create spans. Tracers are responsible for generating spans
	 * within a given instrumentation scope (in this case, "OrderAppTracer").
	 */
	private static final Tracer tracer;

	static {
		// Configure OpenTelemetry SDK with a simple console exporter
		SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
				.addSpanProcessor(SimpleSpanProcessor.create(LoggingSpanExporter.create())).build();

		// Register the SDK globally so all OpenTelemetry API calls use it
		OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

		// Obtain a tracer instance for this application
		tracer = GlobalOpenTelemetry.getTracer("OrderAppTracer");
	}

	/**
	 * The entry point of the application.
	 *
	 * <p>
	 * It demonstrates how to:
	 * <ol>
	 * <li>Create a parent span representing the overall process.</li>
	 * <li>Create child spans for individual steps (order validation and payment
	 * processing).</li>
	 * <li>Use try-with-resources and {@link Scope} to ensure spans are correctly
	 * associated with the current thread.</li>
	 * <li>End each span properly so that it is exported.</li>
	 * </ol>
	 *
	 * @param args command-line arguments (not used here).
	 */
	public static void main(String[] args) {
		// Start a parent span representing the entire order process
		Span parentSpan = tracer.spanBuilder("process-order").startSpan();

		try (Scope scope = parentSpan.makeCurrent()) {
			System.out.println("Processing order inside trace...");

			// Child span for order validation
			Span childSpan = tracer.spanBuilder("validate-order").startSpan();
			try (Scope childScope = childSpan.makeCurrent()) {
				System.out.println("Validating order...");
			} finally {
				childSpan.end(); // Must always end spans
			}

			// Child span for payment processing
			Span paymentSpan = tracer.spanBuilder("process-payment").startSpan();
			try (Scope payScope = paymentSpan.makeCurrent()) {
				System.out.println("Processing payment...");
			} finally {
				paymentSpan.end();
			}
		} finally {
			// End the parent span after all children are completed
			parentSpan.end();
		}

		System.out.println("Trace recorded");
	}
}
