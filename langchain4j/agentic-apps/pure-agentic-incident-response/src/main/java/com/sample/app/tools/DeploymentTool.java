package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class DeploymentTool {

  @Tool(
      """
            Returns information about the latest deployment
            for the requested service.
            """)
  public String getLatestDeployment(@P("service name") String service) {

    System.out.println("[TOOL] Checking deployment for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
                    Latest deployment:

                    Service:
                    checkout-service

                    New version:
                    2.7.4

                    Previous version:
                    2.7.3

                    Deployment started:
                    16:04

                    Deployment completed:
                    16:08

                    Health checks:
                    PASSED

                    No other deployment occurred
                    during the previous 24 hours.
                    """;
    }

    return """
                No recent deployment found.
                """;
  }

  @Tool(
      """
            Returns configuration changes introduced by
            the latest deployment.
            """)
  public String getConfigurationChanges(@P("service name") String service) {

    System.out.println("[TOOL] Checking configuration changes for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
                    Configuration changes in version 2.7.4:

                    DB_POOL_MAX
                    Previous: 100
                    Current : 50

                    DB_CONNECTION_TIMEOUT_MS
                    Previous: 5000
                    Current : 30000

                    PAYMENT_API_TIMEOUT_MS
                    Unchanged

                    JVM heap
                    Unchanged

                    Replica count
                    Unchanged

                    Change description:

                    "Reduce idle DB connections
                    in checkout-service."
                    """;
    }

    return """
                No significant configuration changes detected.
                """;
  }
}
