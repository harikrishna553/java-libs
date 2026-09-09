package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryCreatorAgent {

  @UserMessage(
      """
			You are a creative story writer.

			Create an initial story based on the user's story idea.

			STORY IDEA:
			{{request}}

			SUPERVISOR GUIDANCE:
			{{supervisorContext}}

			Requirements:

			- Follow the supervisor guidance carefully.
			- Create an engaging beginning, middle, and ending.
			- Use clear and readable language.
			- Keep the story family-friendly.
			- Return only the story.
			- Do not provide explanations or review comments.
			""")
  @Agent("Creates the initial story from the user's story idea")
  String createStory(
      @V("request") String request, @V("supervisorContext") String supervisorContext);
}
