package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryWriter {

  @UserMessage(
      """
			Create a short story based on the given idea.

			Story Idea:
			{{storyIdea}}

			Target Audience:
			{{targetAudience}}

			Requirements:
			- Adapt the language to the target audience.
			- Give the story a suitable title.
			- Include a beginning, middle, and ending.
			- Include a positive message.
			- Keep the story engaging and easy to understand.

			Return only the story.
			""")
  @Agent(
      name = "storyWriter",
      description = "Creates a story for the specified target audience.",
      outputKey = "story")
  String writeStory(
      @V("storyIdea") String storyIdea,
      @V("targetAudience") String targetAudience,
      @V("style") String style);
}
