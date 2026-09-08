package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class MonitoringTool {

  @Tool(
      """
			Returns the current production runtime metrics
			for the requested service.
			""")
  public String getMetrics(@P("service name") String service) {

    System.out.println("[TOOL] Reading metrics for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
					Metrics for checkout-service:

					HTTP 5xx error rate:
					31.4%

					Normal 5xx error rate:
					below 0.5%

					p95 latency:
					1.82 seconds

					Normal p95 latency:
					approximately 240 ms

					Application CPU:
					48%

					Application memory:
					61%

					Database connection pool:
					50 active / 50 maximum

					Waiting DB requests:
					112

					DB connection acquisition timeouts:
					rapidly increasing
					""";
    }

    return """
				Metrics are within normal operating range.
				""";
  }

  @Tool(
      """
			Returns the metric timeline around the
			production incident.
			""")
  public String getMetricTimeline(@P("service name") String service) {

    System.out.println("[TOOL] Reading metric timeline for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
					Metric timeline:

					16:00
					System healthy

					16:08
					New deployment completed

					16:10
					DB connection-pool utilization starts rising

					16:12
					DB connection pool reaches 100%

					16:13
					HTTP 500 errors start increasing

					16:16
					Error rate reaches approximately 30%
					""";
    }

    return """
				No significant metric changes detected.
				""";
  }
}
