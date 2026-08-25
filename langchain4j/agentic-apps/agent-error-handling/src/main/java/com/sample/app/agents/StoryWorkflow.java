package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface StoryWorkflow {

  @Agent
  String writeStory(@V("request") String request);
}
