package com.sample.app.signals.metrics;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.metrics.Meter;
import io.opentelemetry.api.metrics.ObservableLongUpDownCounter;
import io.opentelemetry.sdk.OpenTelemetrySdk;
import io.opentelemetry.sdk.metrics.SdkMeterProvider;
import io.opentelemetry.sdk.metrics.export.PeriodicMetricReader;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.time.Duration;
import java.util.Random;

public class AsyncUpDownCounterExample {

    private static final Meter meter;

    static {
        // Configure OpenTelemetry with exporter and reader
        SdkMeterProvider meterProvider = SdkMeterProvider.builder()
                .registerMetricReader(
                        PeriodicMetricReader.builder(OtlpJsonLoggingMetricExporter.create())
                                .setInterval(Duration.ofSeconds(2)) // poll every 2s
                                .build()
                )
                .build();

        OpenTelemetrySdk.builder().setMeterProvider(meterProvider).buildAndRegisterGlobal();
        meter = GlobalOpenTelemetry.getMeter("com.sample.app.signals.metrics");
    }

    public static void main(String[] args) throws InterruptedException {
        ThreadMXBean threadBean = ManagementFactory.getThreadMXBean();

        // Build an asynchronous up down counter
        ObservableLongUpDownCounter threadCounter = meter.upDownCounterBuilder("jvm.threads.live")
                .setDescription("Tracks the number of live threads in the JVM")
                .setUnit("1")
                .buildWithCallback(measurement -> {
                    int liveThreads = threadBean.getThreadCount();
                    measurement.record(liveThreads);
                    System.out.println("Observed live threads: " + liveThreads);
                });
        
        for(int i = 0; i < 10; i++) {
        	Thread t1 = new Thread() {
        		public void run() {
        			try {
						Thread.sleep(new Random().nextInt(1000, 10000));
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        		}
        	};
        	
        	t1.start();
        }

        // Keep the application alive long enough to see a few exports
        Thread.sleep(10000);

        // Clean up when shutting down
        threadCounter.close();
    }
}
