package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ContentWriter {

  @UserMessage(
      """
        Create content based on the following request.

        Request:
        {{request}}

        Content Type:
        {{contentType}}

        Target Audience:
        {{audience}}

        Tone:
        {{tone}}

        Requirements:
        - Follow the requested content type.
        - Write for the specified audience.
        - Maintain the specified tone.
        - Make the content clear and well structured.

        Return only the generated content.
        """)
  @Agent(
      name = "contentWriter",
      description = "Creates the initial content based on the user request and workflow settings.",
      outputKey = "draft")
  String writeContent(
      @V("request") String request,
      @V("contentType") String contentType,
      @V("audience") String audience,
      @V("tone") String tone);
}
