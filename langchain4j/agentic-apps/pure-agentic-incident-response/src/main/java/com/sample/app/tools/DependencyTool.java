package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class DependencyTool {

  @Tool(
      """
            Returns health information for downstream
            dependencies used by the service.
            """)
  public String getDependencyHealth(@P("service name") String service) {

    System.out.println("[TOOL] Checking dependencies for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
                    Dependency health:

                    PostgreSQL
                    HEALTHY

                    CPU:
                    41%

                    Connections:
                    412 / 1200

                    Query latency:
                    NORMAL


                    payment-api
                    HEALTHY

                    p95 latency:
                    118 ms

                    Error rate:
                    0.1%


                    inventory-api
                    HEALTHY


                    Redis
                    HEALTHY


                    Kafka
                    HEALTHY
                    """;
    }

    return """
                All downstream dependencies are healthy.
                """;
  }

  @Tool(
      """
            Checks database server health independently
            from the application's local connection pool.
            """)
  public String getDatabaseHealth(@P("service name") String service) {

    System.out.println("[TOOL] Checking database for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
                    PostgreSQL database:

                    Status:
                    HEALTHY

                    CPU:
                    41%

                    Active connections:
                    412 / 1200

                    Query latency:
                    NORMAL

                    Lock waits:
                    NORMAL

                    Replication lag:
                    0.4 seconds

                    Database server is not saturated.
                    """;
    }

    return """
                Database is healthy.
                """;
  }
}
