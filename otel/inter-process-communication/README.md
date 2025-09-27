# Distributed Tracing with OpenTelemetry (Spring Boot + W3C Trace Context)

This demo shows how to achieve **trace continuity across services** using OpenTelemetry and the **W3C Trace Context** standard.

We build two simple Spring Boot applications:

- **Service A (Caller)** → Exposes `/call`, starts a client span, and makes an HTTP call to Service B.  
- **Service B (Receiver)** → Exposes `/process`, extracts the `traceparent` header from the incoming request, and starts a server span.  

Both services share the same **trace ID**, proving that the request is part of a single distributed trace.

---

## 📝 What You’ll Learn

- How OpenTelemetry propagates tracing information across service boundaries.  
- How the `traceparent` and `tracestate` headers carry trace context.  
- Why **trace continuity** is crucial for debugging and observability in distributed systems.  

---

## ⚙️ Prerequisites

- JDK 17+  
- Maven or Gradle  
- Docker (optional, if you want to run an observability backend like Jaeger/Tempo)  

---

## 🚀 Running the Demo

1. **Start Service B** (receiver)  

   Build and run:  

   ```bash
   cd service-b
   mvn clean package
   java -Dserver.port=8081 -jar ./target/service-b-0.0.1-SNAPSHOT.jar
   ```

   Service B will start on `http://localhost:8081/process`.

2. **Start Service A** (caller)  

   Build and run:  

   ```bash
   cd service-a
   mvn clean package
   java -jar ./target/service-a-0.0.1-SNAPSHOT.jar
   ```

   Service A will start on `http://localhost:8080/call`.

3. **Trigger the flow**  

   ```bash
   curl http://localhost:8080/call
   ```

---

## 📜 What Happens Under the Hood

1. A client request hits **Service A** at `/call`.  
2. Service A creates a **new span** and injects its context into the HTTP headers.  

   Example injected header:  

   ```
   traceparent: 00-4bf92f3577b34da6a3ce929d0e0e4736-a111111111111111-01
   tracestate: vendorX=abc123
   ```

3. Service B receives the request at `/process`, extracts the context, and creates its own span.  
4. Both spans share the **same trace ID**. Service B’s span uses Service A’s span ID as its **parent**.  

---

## ✅ Expected Outcome

- Service A logs something like:  

  ```
  INFO Service A: Started span, traceId=4bf92f3577b34da6a3ce929d0e0e4736, spanId=a111111111111111
  ```

- Service B logs something like:  

  ```
  INFO Service B: Received traceparent=00-4bf92f3577b34da6a3ce929d0e0e4736-a111111111111111-01
  INFO Service B: Started span, traceId=4bf92f3577b34da6a3ce929d0e0e4736, parentSpanId=a111111111111111
  ```

Together, these logs confirm that both services participate in the **same distributed trace**.

---

## 📦 Optional: Visualize the Trace

If you run an observability backend (e.g., Jaeger, Tempo, or Zipkin), configure both services with the OTLP exporter.  
You’ll then see a **single trace tree** where Service A’s span is the parent and Service B’s span is the child.

---

## 🔑 Key Takeaway

This demo proves how the **W3C Trace Context** standard (`traceparent` + `tracestate`) enables **end-to-end tracing** across services — even when they are separate processes or written in different languages.
