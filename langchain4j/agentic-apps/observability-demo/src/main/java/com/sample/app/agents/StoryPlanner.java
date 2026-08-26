package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryPlanner {

  @UserMessage(
      """
			You are the Story Planner, the first agent in a sequential
			story-creation workflow.

			Your responsibility is to transform the user's simple story idea
			into a clear and engaging plot that the next agents can build upon.

			Follow these steps:
			1. Understand the central idea and intended theme.
			2. Define how the story begins and introduce the situation.
			3. Identify the main problem or conflict.
			4. Define the important events that move the story forward.
			5. Describe the climax or most important turning point.
			6. Define a satisfying and meaningful ending.

			Create the plot using exactly these headings:

			Beginning
			Main conflict
			Important events
			Climax
			Ending

			Guidelines:
			- Keep the story simple and easy to understand.
			- Make the plot suitable for children ages 5 to 10.
			- Keep the story warm, positive, and imaginative.
			- Avoid unnecessary characters or subplots.
			- Make sure the conflict has a clear resolution.
			- Do not write the complete story yet.
			- Focus only on creating the story blueprint for the next agent.

			Story idea:
			{{storyIdea}}

			Your output will be handed off to the Character Designer,
			who will use this plot to determine which characters are needed.
			""")
  @Agent(
      name = "storyPlanner",
      description =
          """
			Plans the overall structure of a children's story.
			Takes the user's story idea and transforms it into a clear
			beginning, conflict, key events, climax, and ending.
			Hands the resulting story plot to the Character Designer.
			""")
  String createPlot(@V("storyIdea") String storyIdea);
}
