package com.sample.app.agents;

import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.service.V;

public interface ContentCreationWorkflow {

  @SequenceAgent(
      name = "contentCreationWorkflow",
      outputKey = "content",
      subAgents = {ContentRouter.class, ContentExpertRouter.class})
  String createContent(@V("request") String request);
}
