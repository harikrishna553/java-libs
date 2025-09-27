package com.sample.app.controller;

import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.propagation.TextMapSetter;
import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.Scope;
import io.opentelemetry.api.trace.SpanKind;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class CallerController {

	private final Tracer tracer;
	private final RestTemplate restTemplate;

	public CallerController(Tracer tracer, RestTemplateBuilder builder) {
		this.tracer = tracer;
		this.restTemplate = builder.build(); // prefer RestTemplateBuilder to keep Spring features
	}

	private static final TextMapSetter<HttpHeaders> setter = (carrier, key, value) -> carrier.set(key, value);

	@GetMapping("/call")
	public String call() {
		Span span = tracer.spanBuilder("service-a.call").setSpanKind(SpanKind.CLIENT).startSpan();

		try (Scope scope = span.makeCurrent()) {
			// Inject tracing headers into outgoing request
			HttpHeaders headers = new HttpHeaders();
			GlobalOpenTelemetry.getPropagators().getTextMapPropagator().inject(Context.current(), headers, setter);

			HttpEntity<String> req = new HttpEntity<>(headers);
			ResponseEntity<String> resp = restTemplate.exchange("http://localhost:8081/process", HttpMethod.GET, req,
					String.class);

			String traceId = span.getSpanContext().getTraceId();
			return "Service A called B — response: " + resp.getBody() + " (traceId=" + traceId + ")";
		} finally {
			span.end();
		}
	}
}
