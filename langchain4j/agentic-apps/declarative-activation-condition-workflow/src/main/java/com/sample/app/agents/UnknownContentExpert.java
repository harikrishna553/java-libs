package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface UnknownContentExpert {

  @UserMessage(
      """
			The following request could not be classified as
			BLOG, SOCIAL_MEDIA, or EMAIL.

			Request:
			{{request}}

			Respond politely and ask the user to specify whether
			they want a blog, social media post, or email.
			""")
  @Agent(
      name = "unknownContentExpert",
      description = "Handles unsupported or unclear content requests",
      outputKey = "content")
  String handle(@V("request") String request);
}
