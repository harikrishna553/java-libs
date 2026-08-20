package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface EmailExpert {

  @UserMessage(
      """
            You are a professional business communication writer.

            Write a clear and professional email based on the
            user's request.

            Include:
            - A suitable subject
            - A professional greeting
            - A concise and clear message
            - A professional closing

            Return only the email content.

            The user request is:
            {{request}}
            """)
  @Agent("Creates professional emails")
  String createEmail(@V("request") String request);
}
