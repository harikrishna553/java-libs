# 📖 README.md — Hello Telemetry (Zero-Code Instrumentation Demo)

## 📌 Overview
This is a simple **Spring Boot demo application** instrumented with **OpenTelemetry** using the **Java Agent** (zero-code instrumentation).  
With the agent attached, you don’t need to modify your code — the agent automatically instruments supported frameworks (like Tomcat, HTTP clients, JDBC, etc.).

In this demo:
- We run a Spring Boot app (`/hello` endpoint).
- The app makes an outbound call to [httpbin.org](https://httpbin.org/get).
- OpenTelemetry agent captures telemetry automatically.
- Telemetry is exported to the **console** for inspection.

---

## ⚙️ Prerequisites
- Java 17 or later (Java 8+ supported by the agent)
- Maven (to build the Spring Boot app)
- Internet connection (for outbound HTTP call to `httpbin.org`)

---

## 📥 Download OpenTelemetry Java Agent
Download the latest OpenTelemetry Java agent, go to the link  https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/ and download latest opentelemetry-javaagent.jar file.


This file enables automatic instrumentation.

## Build the Spring Boot App

Clone or create the demo app and build it:

```bash
mvn clean package -DskipTests
```
The JAR will be created at target folder.

## Run with OpenTelemetry Agent
Run the app with the agent attached:

```bash
java \
  -javaagent:./opentelemetry-javaagent.jar \
  -Dotel.service.name=hello-telemetry \
  -Dotel.traces.exporter=logging \
  -Dotel.metrics.exporter=none \
  -Dotel.logs.exporter=none \
  -jar ./target/zero-code-instrumentation-0.0.1-SNAPSHOT.jar
```
## Try the Demo
In a new terminal, call the app endpoint:

```bash
curl http://localhost:8080/hello
```

## Sample Telemetry Logs
When you run the app, you’ll see logs like this:

```bash
[otel.javaagent 2025-09-26 11:06:26:117 +0530] [http-nio-8080-exec-1] INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'GET' : efffb8e3b482b1dba38619bdd5523d98 ffd3f4d3b10fe964 CLIENT [tracer: io.opentelemetry.http-url-connection:2.20.1-alpha] AttributesMap{data={http.response.status_code=200, thread.id=47, thread.name=http-nio-8080-exec-1, url.full=https://httpbin.org/get, server.address=httpbin.org, http.request.method=GET, network.protocol.version=1.1}, capacity=128, totalAddedValues=7}

[otel.javaagent 2025-09-26 11:06:26:129 +0530] [http-nio-8080-exec-1] INFO io.opentelemetry.exporter.logging.LoggingSpanExporter - 'GET /hello' : efffb8e3b482b1dba38619bdd5523d98 4321366be0b06bfd SERVER [tracer: io.opentelemetry.tomcat-10.0:2.20.1-alpha] AttributesMap{data={http.response.status_code=200, thread.id=47, url.scheme=http, thread.name=http-nio-8080-exec-1, server.port=8080, user_agent.original=curl/8.7.1, network.protocol.version=1.1, network.peer.port=57184, server.address=localhost, client.address=0:0:0:0:0:0:0:1, network.peer.address=0:0:0:0:0:0:0:1, url.path=/hello, http.request.method=GET, http.route=/hello}, capacity=128, totalAddedValues=14}

```

