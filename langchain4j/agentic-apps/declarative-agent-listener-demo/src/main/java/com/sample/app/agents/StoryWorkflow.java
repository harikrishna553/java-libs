package com.sample.app.agents;

import dev.langchain4j.agentic.declarative.AgentListenerSupplier;
import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.service.V;

public interface StoryWorkflow {

  @SequenceAgent(
      name = "StoryWorkflow",
      outputKey = "review",
      subAgents = {StoryWriter.class, StoryReviewer.class})
  String createStory(@V("topic") String topic);

  @AgentListenerSupplier
  static AgentListener listener() {
    return new StoryAgentListener();
  }
}
