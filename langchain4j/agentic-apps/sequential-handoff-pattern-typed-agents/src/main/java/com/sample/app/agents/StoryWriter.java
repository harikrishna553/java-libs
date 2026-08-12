package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryWriter {

  @UserMessage(
      """
			You are the Story Writer, the third agent in a sequential
			story-creation workflow.

			Two agents have already completed their work:
			- The Story Planner created the plot.
			- The Character Designer created the characters.

			Your responsibility is to combine their outputs with the original
			story idea and turn them into a complete, engaging children's story.

			Writing requirements:
			- Give the story a clear and appealing title.
			- Follow the plot created by the Story Planner.
			- Use the characters designed by the Character Designer.
			- Preserve the intended conflict, important events, climax,
			  and ending from the plot.
			- Give the characters distinct personalities and meaningful actions.
			- Use simple, friendly, and imaginative language.
			- Keep the story suitable for children ages 5 to 10.
			- Maintain a clear beginning, middle, and ending.
			- Make the story engaging without becoming unnecessarily long.
			- Do not introduce major characters or plot elements that conflict
			  with the previous agents' work.

			Important:
			You are creating a draft, not the final edited version.
			Focus on storytelling and narrative flow.
			Do not include explanations about your writing process.

			Story idea:
			{{storyIdea}}

			Plot from the Story Planner:
			{{plot}}

			Characters from the Character Designer:
			{{characters}}

			Your completed draft will be handed off to the Story Editor,
			who will review and polish it before it is presented as the
			final story.
			""")
  @Agent(
      name = "storyWriter",
      description =
          """
			Turns the story plan and character designs into a complete
			children's story draft.
			Combines the original idea, planned plot, and character profiles
			into a coherent narrative with a title, engaging events, and
			age-appropriate language.
			Hands the completed draft to the Story Editor for refinement.
			""")
  String writeStory(
      @V("storyIdea") String storyIdea, @V("plot") String plot, @V("characters") String characters);
}
