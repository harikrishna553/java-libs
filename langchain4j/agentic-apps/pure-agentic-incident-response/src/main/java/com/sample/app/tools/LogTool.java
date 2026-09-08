package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class LogTool {

  @Tool(
      """
			Searches recent production error logs
			for the requested service.
			""")
  public String searchErrors(@P("service name") String service) {

    System.out.println("[TOOL] Searching logs for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
					Dominant production error:

					java.sql.SQLTransientConnectionException:

					HikariPool-1 -
					Connection is not available,
					request timed out after 30000ms.

					CheckoutController:

					Failed to create checkout session
					because a database connection could
					not be acquired.

					Occurrences:
					2,841 errors in 10 minutes

					First occurrence:
					16:13
					""";
    }

    return """
				No significant recurring errors found.
				""";
  }

  @Tool(
      """
			Returns startup and configuration-related
			application logs.
			""")
  public String getStartupLogs(@P("service name") String service) {

    System.out.println("[TOOL] Reading startup logs for: " + service);

    if ("checkout-service".equalsIgnoreCase(service)) {

      return """
					checkout-service version 2.7.4

					Application started:
					16:08

					HikariCP configuration:

					maximumPoolSize=50

					connectionTimeout=30000

					Application startup completed successfully.

					No JVM OutOfMemoryError.

					No crash-loop detected.
					""";
    }

    return """
				Application started normally.
				""";
  }
}
