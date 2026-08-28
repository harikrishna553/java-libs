package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface EmailExpert {

  @UserMessage(
      """
            You are a professional email writer.

            Create an email based on the following request:

            {{request}}

            Requirements:

            - Provide a suitable subject.
            - Use a professional greeting.
            - Keep the message clear and concise.
            - Use an appropriate closing.

            Return only the final email.
            """)
  @Agent(
      name = "emailExpert",
      description = "Creates professional email content",
      outputKey = "content")
  String createEmail(@V("request") String request);
}
