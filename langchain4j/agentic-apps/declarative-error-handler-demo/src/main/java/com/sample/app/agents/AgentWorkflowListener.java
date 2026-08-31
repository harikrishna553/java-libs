package com.sample.app.agents;

import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentResponse;
import dev.langchain4j.agentic.scope.AgenticScope;

public class AgentWorkflowListener implements AgentListener {

  @Override
  public void afterAgentInvocation(AgentResponse agentResponse) {

    AgenticScope agenticScope = agentResponse.agenticScope();

    System.out.println();
    System.out.println("======================================");
    System.out.println("Agent Invocation Completed");
    System.out.println("======================================");

    System.out.println("Agent Name : " + agentResponse.agentName());

    System.out.println();
    System.out.println("AgenticScope State:");
    System.out.println("--------------------------------------");

    agenticScope.state().forEach((key, value) -> System.out.println(key + " = " + value));

    System.out.println("======================================");
  }

  @Override
  public boolean inheritedBySubagents() {
    return true;
  }
}
