package com.sample.app.agents;

import com.sample.app.model.ServiceHealth;
import com.sample.app.model.Status;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public class PriorityCalculatorAgent {

  @Agent(
      value =
          """
                    Calculates incident priority using
                    deterministic operational rules
                    """,
      outputKey = "priority")
  public String calculatePriority(@V("serviceHealth") ServiceHealth serviceHealth) {

    System.out.println();
    System.out.println(">>> NON-AI AGENT: PriorityCalculatorAgent");

    double errorRate = serviceHealth.getErrorRate();

    if (serviceHealth.getStatus() == Status.DOWN) {

      return "P1";
    }

    if (errorRate >= 20) {
      return "P1";
    }

    if (errorRate >= 10) {
      return "P2";
    }

    if (errorRate >= 5) {
      return "P3";
    }

    return "P4";
  }
}
