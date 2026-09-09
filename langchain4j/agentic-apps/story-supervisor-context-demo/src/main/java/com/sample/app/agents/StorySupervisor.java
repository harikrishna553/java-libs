package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface StorySupervisor {

  @Agent
  String createStory(@V("request") String request, @V("qualityThreshold") int qualityThreshold);
}
