package com.sample.app.agents;

import com.sample.app.console.ConsoleRenderer;
import dev.langchain4j.agentic.observability.AfterAgentToolExecution;
import dev.langchain4j.agentic.observability.AgentInvocationError;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.agentic.observability.BeforeAgentToolExecution;
import dev.langchain4j.agentic.scope.AgenticScope;

public class AgentWorkflowListener implements AgentListener {

  private static final int MAX_DISPLAY_LENGTH = 100;

  private static final String LINE =
      "================================================================";

  @Override
  public void beforeAgentInvocation(AgentRequest request) {

    // Ignore composite/container agents.
    if (!request.agent().leaf()) {
      return;
    }

    printInitialStateIfNeeded(request);

    String outputKey = displayValue(request.agent().outputKey());
    String agentName = request.agent().name();

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("▶  AGENT INVOCATION");

    System.out.println(LINE);
    System.out.println();

    ConsoleRenderer.info("Agent      : " + agentName);
    ConsoleRenderer.info("Output Key : " + outputKey);

    System.out.println();
    ConsoleRenderer.thinking();
  }

  @Override
  public void afterAgentInvocation(AgentResponse response) {

    // Ignore composite/container agents.
    if (!response.agent().leaf()) {
      return;
    }

    String outputKey = response.agent().outputKey();
    AgenticScope scope = response.agenticScope();

    /*
     * Store the original output in the workflow state. Trimming is only applied
     * when displaying it.
     */
    scope.writeState(outputKey, response.output());

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("✓  AGENT COMPLETED");

    System.out.println(LINE);
    System.out.println();

    ConsoleRenderer.info("Output Key : " + displayValue(outputKey));

    System.out.println();

    /*
     * Display a trimmed version of the agent output.
     */
    ConsoleRenderer.agent("Agent Output\n\n" + displayValue(response.output()));

    System.out.println();

    ConsoleRenderer.info("★  State Updated");

    System.out.println();

    Object stateValue = scope.readState(outputKey, null);

    System.out.println("    " + displayValue(outputKey) + " = " + displayValue(stateValue));

    System.out.println();
  }

  @Override
  public void onAgentInvocationError(AgentInvocationError error) {

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("✗  AGENT INVOCATION FAILED");

    System.out.println(LINE);
    System.out.println();

    System.out.println("    " + displayValue(error));

    System.out.println();
  }

  @Override
  public void afterAgenticScopeCreated(AgenticScope scope) {

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("★  AGENTIC SCOPE CREATED");

    System.out.println(LINE);
    System.out.println();

    ConsoleRenderer.info("Initial Workflow State");

    System.out.println();

    printState(scope);

    System.out.println();
  }

  @Override
  public void beforeAgenticScopeDestroyed(AgenticScope scope) {

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("★  FINAL WORKFLOW STATE");

    System.out.println(LINE);
    System.out.println();

    printState(scope);

    System.out.println();

    System.out.println(LINE);

    ConsoleRenderer.info("✓  WORKFLOW COMPLETED");

    System.out.println(LINE);

    System.out.println();
  }

  @Override
  public void onAgenticSystemSuspended(AgenticScope scope) {

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("⏸  AGENTIC SYSTEM SUSPENDED");

    System.out.println(LINE);
    System.out.println();

    ConsoleRenderer.info("Current Workflow State");

    System.out.println();

    printState(scope);

    System.out.println();
  }

  @Override
  public void beforeAgentToolExecution(BeforeAgentToolExecution execution) {

    System.out.println();

    ConsoleRenderer.info("⚙  TOOL EXECUTION STARTED");

    System.out.println("    " + displayValue(execution));

    System.out.println();
  }

  @Override
  public void afterAgentToolExecution(AfterAgentToolExecution execution) {

    ConsoleRenderer.info("✓  TOOL EXECUTION COMPLETED");

    System.out.println("    " + displayValue(execution));

    System.out.println();
  }

  /** Makes this listener available to all subagents. */
  @Override
  public boolean inheritedBySubagents() {
    return true;
  }

  /** Prints the initial workflow state only once. */
  private void printInitialStateIfNeeded(AgentRequest request) {

    AgenticScope scope = request.agenticScope();

    if (scope.executionContext("initialStatePrinted") != null) {
      return;
    }

    scope.writeExecutionContext("initialStatePrinted", Boolean.TRUE);

    System.out.println();
    System.out.println(LINE);

    ConsoleRenderer.info("INITIAL WORKFLOW STATE");

    System.out.println(LINE);
    System.out.println();

    printState(scope);

    System.out.println();
  }

  /**
   * Prints the current workflow state.
   *
   * <p>The listener does not make any assumptions about application-specific state keys.
   */
  private void printState(AgenticScope scope) {

    scope
        .state()
        .forEach(
            (key, value) -> {
              System.out.println("  " + displayValue(key) + " = " + displayValue(value));
            });
  }

  /**
   * Converts a value to a displayable string and truncates it to keep console output readable.
   *
   * <p>The original value is never modified.
   */
  private String displayValue(Object value) {

    if (value == null) {
      return "<not set>";
    }

    String text = value.toString();

    if (text.length() <= MAX_DISPLAY_LENGTH) {
      return text;
    }

    return text.substring(0, MAX_DISPLAY_LENGTH) + "...";
  }
}
