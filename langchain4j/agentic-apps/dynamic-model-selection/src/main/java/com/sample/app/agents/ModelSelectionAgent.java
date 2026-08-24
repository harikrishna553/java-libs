package com.sample.app.agents;

import com.sample.app.model.ModelType;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ModelSelectionAgent {

  @UserMessage(
      """
        Determine which type of AI model should handle the following user request.

        Return CODING if the request involves:
        - Writing code
        - Debugging code
        - Refactoring code
        - Explaining code
        - Designing a software implementation

        Return GENERAL for all other requests.

        User request:
        {{request}}
        """)
  @Agent("Determines the appropriate model type for the user request")
  ModelType selectModel(@V("request") String request);
}
