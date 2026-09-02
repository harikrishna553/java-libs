package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryReviewer {

  @UserMessage(
      """
            Review the following story:

            {{story}}

            Provide a short review.
            """)
  @Agent(name = "StoryReviewer", outputKey = "review", description = "Reviews the generated story")
  String reviewStory(@V("story") String story);
}
