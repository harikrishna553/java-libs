package com.sample.app.agents;

import com.sample.app.console.ConsoleRenderer;
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

    ConsoleRenderer.info(nodeTitle(request.agent().outputKey()));
    ConsoleRenderer.thinking();
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
    ConsoleRenderer.agent(outputLabel(outputKey) + "\n\n" + response.output());

    // Show the state update.
    ConsoleRenderer.info("State updated: " + outputKey);

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
    ConsoleRenderer.info("workflow state");
    ConsoleRenderer.info("-".repeat(100));

    Map<String, Object> stateObj = scope.state();

    for (String key : stateObj.keySet()) {
      Object value = stateObj.get(key);

      if (value != null) {
        String valueStr = value.toString();
        if (valueStr.length() > 100) {
          valueStr = valueStr.substring(0, 100) + "...";
        }
        ConsoleRenderer.info(key + " -> " + valueStr);
      } else {
        ConsoleRenderer.info(key + " -> null");
      }
    }

    ConsoleRenderer.info("-".repeat(100));
  }

  private String nodeTitle(String outputKey) {
    if ("plot".equals(outputKey)) {
      return "NODE 1 · Story Planner";
    }

    if ("characters".equals(outputKey)) {
      return "NODE 2 · Character Designer";
    }

    if ("draft".equals(outputKey)) {
      return "NODE 3 · Story Writer";
    }

    if ("finalStory".equals(outputKey)) {
      return "NODE 4 · Story Editor";
    }

    return "NODE · " + outputKey;
  }

  private String outputLabel(String outputKey) {
    if ("plot".equals(outputKey)) {
      return "Story Planner Output";
    }

    if ("characters".equals(outputKey)) {
      return "Character Designer Output";
    }

    if ("draft".equals(outputKey)) {
      return "Story Writer Output";
    }

    if ("finalStory".equals(outputKey)) {
      return "Story Editor Output";
    }

    return "Agent Output";
  }
}
