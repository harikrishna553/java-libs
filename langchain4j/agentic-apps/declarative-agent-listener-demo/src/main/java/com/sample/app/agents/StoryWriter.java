package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryWriter {

  @UserMessage(
      """
			Write a short story about {{topic}}.
			Return only the story.
			""")
  @Agent(
      name = "StoryWriter",
      outputKey = "story",
      description = "Writes a story for the given topic")
  String writeStory(@V("topic") String topic);
}
