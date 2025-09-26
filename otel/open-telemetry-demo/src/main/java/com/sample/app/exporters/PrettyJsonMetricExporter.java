package com.sample.app.exporters;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import io.opentelemetry.exporter.logging.otlp.OtlpJsonLoggingMetricExporter;
import io.opentelemetry.sdk.common.CompletableResultCode;
import io.opentelemetry.sdk.metrics.InstrumentType;
import io.opentelemetry.sdk.metrics.data.AggregationTemporality;
import io.opentelemetry.sdk.metrics.data.MetricData;
import io.opentelemetry.sdk.metrics.export.MetricExporter;

import java.io.IOException;
import java.util.Collection;

public class PrettyJsonMetricExporter implements MetricExporter {

	private final MetricExporter delegate = OtlpJsonLoggingMetricExporter.create();
	private final ObjectWriter writer = new ObjectMapper().writerWithDefaultPrettyPrinter();

	@Override
	public CompletableResultCode export(Collection<MetricData> metrics) {
		try {
			// Pretty print JSON
			String json = writer.writeValueAsString(metrics);
			System.out.println(json);
		} catch (IOException e) {
			e.printStackTrace();
		}
		// Still pass metrics to delegate if you want default behavior
		return delegate.export(metrics);
	}

	@Override
	public CompletableResultCode flush() {
		return delegate.flush();
	}

	@Override
	public CompletableResultCode shutdown() {
		return delegate.shutdown();
	}

	@Override
	public AggregationTemporality getAggregationTemporality(InstrumentType instrumentType) {
		return delegate.getAggregationTemporality(instrumentType);
	}
}
