package com.sample.app.listeners;

import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.AgentRequest;
import dev.langchain4j.agentic.observability.AgentResponse;

public class ConsoleAgentListener implements AgentListener {

  @Override
  public void beforeAgentInvocation(AgentRequest request) {

    System.out.println();
    System.out.println("--------------------------------------------------");
    System.out.println("Invoking Agent : " + request.agentName());
    System.out.println("--------------------------------------------------");
  }

  @Override
  public void afterAgentInvocation(AgentResponse response) {

    System.out.println();
    System.out.println("Agent Completed : " + response.agentName());

    if (response.output() instanceof StoryReview review) {

      System.out.println("Quality Score   : " + review.getScore());
      System.out.println("Target Reached  : " + review.isThresholdReached());

      System.out.println();
      System.out.println("Reviewer Feedback:");
      System.out.println(review.getFeedback());
    }
  }

  @Override
  public boolean inheritedBySubagents() {
    return true;
  }
}
