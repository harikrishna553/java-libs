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

    printInitialStateIfNeeded(request);

    System.out.println(request.agent().outputKey());
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
    System.out.println(outputKey + "\n\n" + response.output());

    // Show the state update.
    System.out.println("State updated: " + outputKey);

    System.out.println("  " + outputKey + " = " + scope.readState(outputKey, null));
    System.out.println();
    printState(scope);
  }

  @Override
  public boolean inheritedBySubagents() {
    return true;
  }

  private void printInitialStateIfNeeded(AgentRequest request) {
    AgenticScope scope = request.agenticScope();

    if (scope.executionContext("initialStatePrinted") != null) {
      return;
    }

    /*
     * The workflow starts with storyIdea as the initial state. Store it in the
     * shared AgenticScope so that subsequent agents can build on the state produced
     * by previous agents.
     */
    if (!scope.hasState("storyIdea") && request.inputs().containsKey("storyIdea")) {

      scope.writeState("storyIdea", request.inputs().get("storyIdea"));
    }

    scope.writeExecutionContext("initialStatePrinted", Boolean.TRUE);

    printState(scope);

    System.out.println();
  }

  private void printState(AgenticScope scope) {
    System.out.println("workflow state");
    System.out.println("-".repeat(100));

    Map<String, Object> stateObj = scope.state();

    for (String key : stateObj.keySet()) {
      Object value = stateObj.get(key);

      if (value != null) {
        String valueStr = value.toString();
        if (valueStr.length() > 100) {
          valueStr = valueStr.substring(0, 100) + "...";
        }
        System.out.println(key + " -> " + valueStr);
      } else {
        System.out.println(key + " -> null");
      }
    }

    System.out.println("-".repeat(100));
  }
}
