package com.sample.app.agents;

import com.sample.app.keys.UserRequest;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.K;
import dev.langchain4j.service.UserMessage;

public interface TechnicalExpert {

  @UserMessage(
      """
            You are the Technical Expert Agent.

            Analyze the following technical request.

            Provide a clear, accurate, and practical response.

            Where appropriate:
            - explain the root cause
            - provide implementation guidance
            - provide code examples
            - mention important technical considerations

            User request:

            {{UserRequest}}
            """)
  @Agent(
      name = "TechnicalExpert",
      description =
          "Handles software, programming, system, cloud, database, networking, and technology questions")
  String answer(@K(UserRequest.class) String request);
}
