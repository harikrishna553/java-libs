package com.sample.app.agents;

import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.agentic.scope.AgenticScope;
import java.util.Map;

public class AgentWorkflowListener implements AgentListener {

  @Override
  public void beforeAgentInvocation(AgentRequest request) {
    if (!request.agent().leaf()) {
      return;
    }

    printState(request.agenticScope());
    System.out.println("Thinking........");
  }

  @Override
  public void afterAgentInvocation(AgentResponse response) {
    if (!response.agent().leaf()) {
      return;
    }

    String outputKey = response.agent().outputKey();
    AgenticScope scope = response.agenticScope();

    // Store the agent output in the shared workflow state.
    scope.writeState(outputKey, response.output());

    // Render the agent output.
    System.out.println((outputKey) + " ---> " + response.output());

    // Show the state update.
    System.out.println("State updated: " + outputKey);

    System.out.println("  " + outputKey + " = " + scope.readState(outputKey, null));
    System.out.println();
  }

  @Override
  public boolean inheritedBySubagents() {
    return true;
  }

  private void printState(AgenticScope scope) {
    System.out.println("\n==================== AGENT STATE ====================");

    Map<String, Object> agentState = scope.state();

    if (agentState.isEmpty()) {
      System.out.println("No state available.");
      System.out.println("======================================================");
      return;
    }

    agentState.forEach(
        (key, value) -> {
          System.out.println("\n[" + key + "]");
          System.out.println(value);
        });

    System.out.println("\n======================================================");
  }
}
