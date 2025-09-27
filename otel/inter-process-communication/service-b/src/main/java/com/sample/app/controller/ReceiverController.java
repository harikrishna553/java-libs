package com.sample.app.controller;

import java.util.Collections;
import java.util.Enumeration;
import java.util.Locale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.opentelemetry.api.GlobalOpenTelemetry;
import io.opentelemetry.api.trace.Span;
import io.opentelemetry.api.trace.SpanKind;
import io.opentelemetry.api.trace.Tracer;
import io.opentelemetry.context.Scope;
import io.opentelemetry.context.Context;
import io.opentelemetry.context.propagation.TextMapGetter;
import jakarta.servlet.http.HttpServletRequest;

@RestController
public class ReceiverController {
    private final Tracer tracer;
    private final Logger logger = LoggerFactory.getLogger(ReceiverController.class);

    public ReceiverController(Tracer tracer) {
        this.tracer = tracer;
    }

    private static final TextMapGetter<HttpServletRequest> getter = new TextMapGetter<>() {

        @Override
        public Iterable<String> keys(HttpServletRequest carrier) {
            if (carrier == null) {
                return Collections.emptyList();
            }
            Enumeration<String> headerNames = carrier.getHeaderNames();
            return headerNames == null ? Collections.emptyList() : Collections.list(headerNames);
        }

        @Override
        public String get(HttpServletRequest carrier, String key) {
            if (carrier == null || key == null) {
                return null;
            }
            // headers are case-insensitive
            return carrier.getHeader(key.toLowerCase(Locale.ROOT));
        }
    };

    @GetMapping("/process")
    public String process(HttpServletRequest request) {
        // log raw headers for demo
        String rawTraceParent = request.getHeader("traceparent");
        String rawTraceState = request.getHeader("tracestate");
        logger.info("Received headers - traceparent: {}, tracestate: {}", rawTraceParent, rawTraceState);

        // Extract context from incoming request
        Context extractedContext = GlobalOpenTelemetry.getPropagators()
                .getTextMapPropagator()
                .extract(Context.current(), request, getter);

        // Create a span with the extracted context as parent
        Span span = tracer.spanBuilder("service-b.process")
                .setParent(extractedContext)
                .setSpanKind(SpanKind.SERVER)
                .startSpan();

        try (Scope scope = span.makeCurrent()) {
            String traceId = span.getSpanContext().getTraceId();
            logger.info("Processing request in Service B, traceId={}", traceId);

            // simulate work
            Thread.sleep(50);
            return "processed by service-b, traceId=" + traceId;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return "error";
        } finally {
            span.end();
        }
    }
}
