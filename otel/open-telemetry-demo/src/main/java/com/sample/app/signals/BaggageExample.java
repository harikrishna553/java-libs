package com.sample.app.signals;

import io.opentelemetry.api.baggage.Baggage;
import io.opentelemetry.context.Context;

/**
 * A simple example that demonstrates how to use <b>Baggage</b> in
 * OpenTelemetry.
 *
 * <p>
 * Baggage is a set of key-value pairs that are <i>propagated across service
 * boundaries</i>. Unlike attributes (which are tied to spans), baggage is
 * designed for passing <i>application-level metadata</i> such as user IDs,
 * regions, or tenant information across different services or processes in a
 * distributed system.
 *
 * <h2>How it works</h2>
 * <ul>
 * <li>A {@link Baggage} object is created using a builder and populated with
 * key-value pairs.</li>
 * <li>The baggage is stored in the current {@link Context} using
 * {@link Baggage#storeInContext(Context)}.</li>
 * <li>The context (with baggage attached) is passed to another function to
 * simulate cross-service propagation.</li>
 * <li>Inside the receiving function, baggage is retrieved from the context and
 * read as a {@code Map}.</li>
 * </ul>
 *
 * <h2>Why use baggage?</h2>
 * <p>
 * Baggage can be useful when you want certain metadata (for example,
 * <code>user.id</code> or <code>region</code>) to travel with every request,
 * ensuring that downstream services have access to the same information without
 * needing to explicitly include it in every API call.
 * </p>
 *
 * <p>
 * <b>Note:</b> Use baggage carefully—because it propagates with every request,
 * excessive baggage entries can increase request size and impact performance.
 * </p>
 */
public class BaggageExample {

	/**
	 * The entry point of the application.
	 *
	 * <p>
	 * Creates a baggage with two keys (<code>user.id</code> and
	 * <code>region</code>), attaches it to the current context, and simulates
	 * passing it to another service.
	 * </p>
	 *
	 * @param args command-line arguments (not used here).
	 */
	public static void main(String[] args) {
		// Create baggage with metadata
		Baggage baggage = Baggage.builder().put("user.id", "12345").put("region", "asia-south1").build();

		// Store baggage inside the current context
		Context context = baggage.storeInContext(Context.current());

		// Simulate passing baggage to another function (like another service call)
		simulateServiceCall(context);

		System.out.println("Baggage propagated across services");
	}

	/**
	 * Simulates a downstream service call that receives a context containing
	 * baggage.
	 *
	 * <p>
	 * The baggage is extracted from the context and printed as a Map of key-value
	 * pairs.
	 * </p>
	 *
	 * @param context the context carrying baggage information.
	 */
	private static void simulateServiceCall(Context context) {
		// Extract baggage from the context
		Baggage received = Baggage.fromContext(context);

		// Print the received baggage contents
		System.out.println("Received baggage: " + received.asMap());
	}
}
