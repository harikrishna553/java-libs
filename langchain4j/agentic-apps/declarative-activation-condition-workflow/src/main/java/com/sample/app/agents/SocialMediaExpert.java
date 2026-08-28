package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SocialMediaExpert {

  @UserMessage(
      """
            You are a social media content specialist.

            Create an engaging social media post for the following request:

            {{request}}

            Requirements:

            - Keep the post concise.
            - Make it engaging and easy to read.
            - Use an appropriate call to action when relevant.
            - Add a few relevant hashtags.

            Return only the social media post.
            """)
  @Agent(
      name = "socialMediaExpert",
      description = "Creates engaging social media content",
      outputKey = "content")
  String createPost(@V("request") String request);
}
