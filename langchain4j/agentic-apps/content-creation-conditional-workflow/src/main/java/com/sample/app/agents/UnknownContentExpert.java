package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface UnknownContentExpert {

  @UserMessage(
      """
			You are a general content creation assistant.

			The user's request does not clearly belong to the
			supported content types: blog, social media, or email.

			Analyze the request and provide a helpful response.
			If appropriate, explain what type of content the user
			should request.

			The user request is:
			{{request}}
			""")
  @Agent("Handles unsupported or unknown content requests")
  String handle(@V("request") String request);
}
