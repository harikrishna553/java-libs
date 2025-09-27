package com.sample.app.signals.spans;

import java.time.Instant;
import java.util.UUID;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.common.AttributeKey;
import io.opentelemetry.api.common.Attributes;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanContext;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.StatusCode;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingSpanExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.trace.SdkTracerProvider;
import io.opentelemetry.sdk.trace.export.SimpleSpanProcessor;

/**
 * A beginner-friendly example of how to use OpenTelemetry spans in a ride-hailing
 * application. 
 *
 * <p>This app demonstrates:</p>
 * <ul>
 *   <li>How to create spans with different {@link SpanKind} values (CLIENT, SERVER, PRODUCER, CONSUMER).</li>
 *   <li>How to attach attributes and events to spans.</li>
 *   <li>How to record success and failure states in spans.</li>
 *   <li>How to use links between spans in asynchronous flows.</li>
 *   <li>Exporting spans in JSON format to the console using {@link OtlpJsonLoggingSpanExporter}.</li>
 * </ul>
 *
 * <p>Think of this as a simulation of a customer booking a ride, where:</p>
 * <ul>
 *   <li>The customer books a ride (client span).</li>
 *   <li>The backend assigns a driver (server span).</li>
 *   <li>The booking event is produced and consumed asynchronously (producer/consumer spans).</li>
 * </ul>
 */
public class RideHailingApp {

    /**
     * The tracer is the main entry point to create spans.
     * A tracer is usually named after the logical component of the system.
     */
    private static final Tracer tracer;

    static {
        // Configure OpenTelemetry with a simple console exporter that prints spans as JSON
        SdkTracerProvider tracerProvider = SdkTracerProvider.builder()
                .addSpanProcessor(SimpleSpanProcessor.create(OtlpJsonLoggingSpanExporter.create()))
                .build();

        // Register this tracer provider globally so all parts of the app can use it
        OpenTelemetrySdk.builder().setTracerProvider(tracerProvider).buildAndRegisterGlobal();

        // Obtain a tracer instance named "RideHailingTracer"
        tracer = GlobalOpenTelemetry.getTracer("RideHailingTracer");
    }

    /**
     * Entry point for the application. 
     * Simulates the process of booking a ride by a customer.
     *
     * @param args command line arguments (not used here)
     */
    public static void main(String[] args) {
        bookRide("CUST-42", "Downtown", "Airport");
    }

    /**
     * Simulates the ride booking flow. 
     * This creates the main CLIENT span representing the customer's request.
     *
     * @param customerId ID of the customer booking the ride
     * @param pickup     Pickup location
     * @param drop       Drop location
     */
    static void bookRide(String customerId, String pickup, String drop) {
        // Start the span representing the "bookRide" operation
        Span span = tracer.spanBuilder("bookRide")
                .setSpanKind(SpanKind.CLIENT) // Client initiates the booking
                .startSpan();

        try (Scope scope = span.makeCurrent()) {
            // Add useful attributes to describe this span
            span.setAttribute("customer.id", customerId);
            span.setAttribute("pickup.location", pickup);
            span.setAttribute("drop.location", drop);

            // Mark key milestones as events
            span.addEvent("Ride booking initiated");

            // Call sub-operations
            assignDriver(customerId, pickup, drop);
            sendBookingEvent(customerId, pickup, drop);

            span.addEvent("Ride booking completed");

            // Mark span as successful
            span.setStatus(StatusCode.OK);
        } catch (Exception e) {
            // Record exception details if anything goes wrong
            span.recordException(e);
            span.setStatus(StatusCode.ERROR);
        } finally {
            span.end(); // Always end spans to avoid memory leaks
        }
    }

    /**
     * Simulates assigning a driver for the ride. 
     * Creates a SERVER span because the backend service fulfills this request.
     *
     * @param customerId ID of the customer
     * @param pickup     Pickup location
     * @param drop       Drop location
     */
    static void assignDriver(String customerId, String pickup, String drop) {
        Span span = tracer.spanBuilder("assignDriver")
                .setSpanKind(SpanKind.SERVER) // Backend service
                .startSpan();

        try (Scope scope = span.makeCurrent()) {
            // Fake driver assignment
            String driverId = "DRV-" + (int) (Math.random() * 100);
            span.setAttribute("driver.id", driverId);
            span.setAttribute("driver.rating", 4.8);

            System.out.println("Driver " + driverId + " assigned for customer " + customerId);

            // Add event with driver details
            span.addEvent("Driver assigned",
                    Attributes.of(AttributeKey.stringKey("driver"), driverId),
                    Instant.now());
        } finally {
            span.end();
        }
    }

    /**
     * Simulates sending a ride booking event to a message queue.
     * Creates a PRODUCER span, as this is an async message publishing step.
     *
     * @param customerId ID of the customer
     * @param pickup     Pickup location
     * @param drop       Drop location
     */
    static void sendBookingEvent(String customerId, String pickup, String drop) {
        Span producerSpan = tracer.spanBuilder("sendBookingEvent")
                .setSpanKind(SpanKind.PRODUCER) // Produces a message to a queue
                .startSpan();

        try (Scope scope = producerSpan.makeCurrent()) {
            String eventId = UUID.randomUUID().toString();
            producerSpan.setAttribute("event.id", eventId);
            producerSpan.setAttribute("event.type", "RIDE_BOOKED");

            // Normally we would publish to Kafka or RabbitMQ here
            System.out.println("Event " + eventId + " sent to queue");

            // Simulate the consumer handling this event
            consumeBookingEvent(eventId, producerSpan.getSpanContext());
        } finally {
            producerSpan.end();
        }
    }

    /**
     * Simulates consuming a booking event from a message queue.
     * Creates a CONSUMER span and links it to the PRODUCER span to indicate
     * causal relationship (but not strict parent-child).
     *
     * @param eventId       ID of the booking event
     * @param parentContext Context of the producer span, used to create a link
     */
    static void consumeBookingEvent(String eventId, SpanContext parentContext) {
        Span consumerSpan = tracer.spanBuilder("consumeBookingEvent")
                .setSpanKind(SpanKind.CONSUMER)
                .addLink(parentContext) // Link instead of parent-child to represent async flow
                .startSpan();

        try (Scope scope = consumerSpan.makeCurrent()) {
            consumerSpan.setAttribute("event.id", eventId);
            consumerSpan.setAttribute("queue", "rides-booking");

            consumerSpan.addEvent("Notification sent to driver and customer");
        } finally {
            consumerSpan.end();
        }
    }
}
