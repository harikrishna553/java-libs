package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SocialMediaExpert {

  @UserMessage(
      """
            You are a professional social media content creator.

            Create an engaging social media post based on the
            user's request.

            Keep the content concise and engaging.
            Use appropriate formatting and hashtags when useful.

            Return only the social media post.

            The user request is:
            {{request}}
            """)
  @Agent("Creates social media posts")
  String createPost(@V("request") String request);
}
