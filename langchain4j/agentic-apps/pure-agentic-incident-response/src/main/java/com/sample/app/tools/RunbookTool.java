package com.sample.app.tools;

import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;

public class RunbookTool {

  @Tool(
      """
            Returns the approved production runbook
            for the proposed remediation.

            This tool does not execute the remediation.
            """)
  public String getRunbook(@P("proposed remediation action") String action) {

    System.out.println("[TOOL] Loading runbook for: " + action);

    if (action != null && action.toLowerCase().contains("rollback")) {

      return """
                    Approved rollback procedure:

                    1. Freeze further deployments.

                    2. Roll back to the last
                       known-good application version.

                    3. Verify readiness and
                       liveness checks.

                    4. Monitor:
                       - HTTP 5xx
                       - p95 latency
                       - DB pool saturation

                    5. Observe service health
                       for at least 10 minutes.

                    6. Keep the previous version active
                       while the configuration regression
                       is investigated.
                    """;
    }

    if (action != null && action.toLowerCase().contains("configuration")) {

      return """
                    Configuration recovery procedure:

                    1. Identify previous known-good value.

                    2. Apply change using the standard
                       configuration pipeline.

                    3. Perform gradual rollout.

                    4. Monitor error rate and
                       connection-pool saturation.

                    5. Roll back immediately if
                       service health deteriorates.
                    """;
    }

    return """
                General incident mitigation:

                - Prefer reversible changes.
                - Change one variable at a time.
                - Validate system health after each action.
                - Escalate if evidence is insufficient.
                """;
  }
}
