package com.sample.app.listeners;

import dev.langchain4j.agentic.observability.AfterAgentToolExecution;
import dev.langchain4j.agentic.observability.AgentInvocationError;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.agentic.observability.BeforeAgentToolExecution;
import dev.langchain4j.agentic.scope.AgenticScope;

public class ConsoleAgentListener implements AgentListener {

  @Override
  public void beforeAgentInvocation(AgentRequest request) {

    System.out.println();
    System.out.println("==================================================");
    System.out.println("[AGENT START]");
    System.out.println("==================================================");

    System.out.println("Agent      : " + request.agent());
    System.out.println("Leaf Agent : " + request.agent().leaf());
    System.out.println("Inputs     : " + request.inputs());

    System.out.println();
  }

  @Override
  public void afterAgentInvocation(AgentResponse response) {

    System.out.println();
    System.out.println("--------------------------------------------------");
    System.out.println("[AGENT COMPLETED]");
    System.out.println("--------------------------------------------------");

    System.out.println("Agent      : " + response.agent());
    System.out.println("Leaf Agent : " + response.agent().leaf());
    System.out.println("Output Key : " + response.agent().outputKey());
    System.out.println("Output     : " + response.output());

    System.out.println();
  }

  @Override
  public void onAgentInvocationError(AgentInvocationError error) {

    System.out.println();
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    System.out.println("[AGENT ERROR]");
    System.out.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

    System.out.println("Agent : " + error.agent());
    System.out.println("Error : " + error.error());

    System.out.println();
    System.out.println("----- ROOT CAUSE -----");

    Throwable throwable = error.error();

    while (throwable != null) {
      System.out.println(throwable.getClass().getName() + ": " + throwable.getMessage());

      throwable = throwable.getCause();
    }

    System.out.println();
  }

  @Override
  public void afterAgenticScopeCreated(AgenticScope agenticScope) {

    System.out.println();
    System.out.println("[SCOPE CREATED]");

    System.out.println("AgenticScope created.");
    System.out.println("Initial state: " + agenticScope.state());

    System.out.println();
  }

  @Override
  public void beforeAgenticScopeDestroyed(AgenticScope agenticScope) {

    System.out.println();
    System.out.println("[SCOPE DESTROYED]");

    System.out.println("Final workflow state:");
    System.out.println(agenticScope.state());

    System.out.println();
  }

  @Override
  public void beforeAgentToolExecution(BeforeAgentToolExecution beforeAgentToolExecution) {

    System.out.println();
    System.out.println("  +----------------------------------------------+");
    System.out.println("  | [TOOL START]                                 |");
    System.out.println("  +----------------------------------------------+");

    System.out.println("  Tool : " + beforeAgentToolExecution.toolExecution().request().name());

    System.out.println(
        "  Args : " + beforeAgentToolExecution.toolExecution().request().arguments());

    System.out.println();
  }

  @Override
  public void afterAgentToolExecution(AfterAgentToolExecution afterAgentToolExecution) {

    System.out.println();
    System.out.println("  +----------------------------------------------+");
    System.out.println("  | [TOOL COMPLETED]                             |");
    System.out.println("  +----------------------------------------------+");

    System.out.println("  Tool   : " + afterAgentToolExecution.toolExecution().request().name());

    System.out.println("  Result : " + afterAgentToolExecution.toolExecution().result());

    System.out.println();
  }

  @Override
  public boolean inheritedBySubagents() {

    /*
     * Very important for a composed agent workflow.
     *
     * Returning true means this listener is inherited by the child agents.
     */
    return true;
  }
}
