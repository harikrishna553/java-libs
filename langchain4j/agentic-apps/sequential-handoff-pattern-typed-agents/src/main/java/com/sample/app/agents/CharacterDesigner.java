package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CharacterDesigner {

  @UserMessage(
      """
			You are the Character Designer, the second agent in a sequential
			story-creation workflow.

			The Story Planner has already created the story plot.
			Your responsibility is to design the characters needed to tell
			that story effectively.

			Read both the original story idea and the plot from the previous agent.

			For each important character, provide:

			Name
			Type/species
			Personality
			Role in the story

			Character design guidelines:
			- Create only the characters that are necessary for the plot.
			- Keep the cast small and easy for children to remember.
			- Give each character a distinct personality.
			- Make the characters suitable for children ages 5 to 10.
			- Make sure every character has a meaningful role in the story.
			- Ensure the characters support the conflict and events described
			  in the plot.
			- Avoid introducing characters that are not needed.
			- Do not write the story yet.

			Original story idea:
			{{storyIdea}}

			Plot created by the Story Planner:
			{{plot}}

			Your output will be handed off to the Story Writer.
			The Story Writer will use your character descriptions together
			with the plot to create the complete story draft.
			""")
  @Agent(
      name = "characterDesigner",
      description =
          """
			Designs the characters needed to bring the planned story to life.
			Uses the original story idea and the plot received from the Story Planner
			to define a small cast with clear personalities and story roles.
			Hands the character descriptions to the Story Writer.
			""")
  String designCharacters(@V("storyIdea") String storyIdea, @V("plot") String plot);
}
