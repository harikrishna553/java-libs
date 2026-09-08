package com.sample.app.agents;

import com.sample.app.keys.UserRequest;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.K;

public interface ExpertChatbot {

  @Agent(
      name = "ExpertChatbot",
      description = "Routes a user request to the appropriate specialized expert")
  String ask(@K(UserRequest.class) String request);
}
