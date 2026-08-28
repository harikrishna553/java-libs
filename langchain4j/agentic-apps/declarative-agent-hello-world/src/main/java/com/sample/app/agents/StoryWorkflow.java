package com.sample.app.agents;

import dev.langchain4j.agentic.declarative.AgentListenerSupplier;
import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.agentic.observability.MonitoredAgent;
import dev.langchain4j.service.V;

public interface StoryWorkflow extends MonitoredAgent {

  @SequenceAgent(
      name = "sequentialStoryWorkflow",
      outputKey = "story",
      subAgents = {StoryWriter.class, StoryImprovementLoop.class})
  String createStory(@V("storyIdea") String storyIdea);

  @AgentListenerSupplier
  static AgentListener listener() {
    return new AgentWorkflowListener();
  }
}
