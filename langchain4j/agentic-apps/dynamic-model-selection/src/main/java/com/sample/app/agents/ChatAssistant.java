package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ChatAssistant {

  @UserMessage(
      """
        Answer the following user request:

        {{request}}
        """)
  @Agent("Answers the user's request using the dynamically selected model")
  String answer(@V("request") String request);
}
