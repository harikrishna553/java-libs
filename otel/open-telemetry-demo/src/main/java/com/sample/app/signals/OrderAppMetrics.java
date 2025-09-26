package com.sample.app.signals;

import java.util.concurrent.TimeUnit;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.LongCounter;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.exporter.logging.LoggingMetricExporter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;

/**
 * OrderAppMetrics is a simple demonstration of using OpenTelemetry Metrics in a Java application.
 * <p>
 * This example shows how to:
 * <ul>
 *     <li>Create a Meter for tracking metrics.</li>
 *     <li>Create and use LongCounters to count events (orders processed, payments charged).</li>
 *     <li>Export metrics to the console using LoggingMetricExporter.</li>
 *     <li>Force a flush to ensure metrics are exported immediately before program exit.</li>
 * </ul>
 * <p>
 * This is intended for beginners who want to see how metrics work in OpenTelemetry Java SDK.
 */
public class OrderAppMetrics {

    /** OpenTelemetry Meter for creating and managing metrics */
    private static final Meter meter;

    /** Counter to track total orders processed */
    private static final LongCounter orderCounter;

    /** Counter to track total payments charged */
    private static final LongCounter paymentCounter;

    /** MeterProvider that manages metric collection and export */
    private static SdkMeterProvider meterProvider;

    static {
        // Initialize the OpenTelemetry SDK for metrics
        // Using LoggingMetricExporter to print metrics to console periodically
        meterProvider = SdkMeterProvider.builder()
                .registerMetricReader(PeriodicMetricReader.builder(LoggingMetricExporter.create()).build())
                .build();

        // Register the SDK globally so it can be accessed via GlobalOpenTelemetry
        OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();

        // Get a Meter instance to create metrics
        meter = GlobalOpenTelemetry.getMeter("OrderAppMetrics");

        // Create a counter to track number of orders processed
        orderCounter = meter.counterBuilder("orders_processed_total")
                .setDescription("Total number of orders processed")
                .setUnit("1")
                .build();

        // Create a counter to track number of payments charged
        paymentCounter = meter.counterBuilder("payments_charged_total")
                .setDescription("Total number of payments charged")
                .setUnit("1")
                .build();
    }

    /**
     * Main method to simulate processing multiple orders and charging payments.
     * <p>
     * Demonstrates how to increment metrics and flush them before the program exits.
     *
     * @param args Command-line arguments (not used in this example)
     * @throws InterruptedException if the flush operation is interrupted
     */
    public static void main(String[] args) throws InterruptedException {
        // Simulate processing several orders
        processOrder("ORD-123", 2);
        processOrder("ORD-124", 1);
        processOrder("ORD-125", 4);
        processOrder("ORD-126", 17);
        processOrder("ORD-127", 1);

        // Force flush metrics to ensure LoggingMetricExporter prints them immediately
        meterProvider.forceFlush().join(2, TimeUnit.SECONDS);
    }

    /**
     * Processes an order and increments the orders counter.
     *
     * @param orderId  Unique identifier for the order
     * @param quantity Number of items in the order
     */
    static void processOrder(String orderId, int quantity) {
        System.out.println("Processing order " + orderId + " with quantity " + quantity);
        // Increment the order counter by 1
        orderCounter.add(1);

        // Charge payment for the order
        chargePayment(orderId);
    }

    /**
     * Charges payment for a given order and increments the payment counter.
     *
     * @param orderId Unique identifier for the order
     */
    static void chargePayment(String orderId) {
        System.out.println("Charging payment for " + orderId);
        // Increment the payment counter by 1
        paymentCounter.add(1);
    }
}
