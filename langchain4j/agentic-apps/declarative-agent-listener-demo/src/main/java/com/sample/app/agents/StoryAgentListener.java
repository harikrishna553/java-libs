package com.sample.app.agents;

import dev.langchain4j.agentic.observability.AgentInvocationError;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;

public class StoryAgentListener implements AgentListener {

  @Override
  public void beforeAgentInvocation(AgentRequest request) {

    System.out.println("Starting Agent : " + request.agentName());

    System.out.println("Inputs         : " + request.inputs());
  }

  @Override
  public void afterAgentInvocation(AgentResponse response) {

    System.out.println("Completed Agent: " + response.agentName());

    System.out.println("Output         : " + response.output());
  }

  @Override
  public void onAgentInvocationError(AgentInvocationError error) {

    System.out.println("Agent Failed   : " + error.agentName());

    System.out.println("Error          : " + error.error().getMessage());
  }

  @Override
  public boolean inheritedBySubagents() {
    return true;
  }
}
