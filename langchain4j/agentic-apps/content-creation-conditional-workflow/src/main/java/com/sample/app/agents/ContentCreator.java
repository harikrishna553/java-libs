package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.V;

public interface ContentCreator {

  @Agent
  String create(@V("request") String request);
}
