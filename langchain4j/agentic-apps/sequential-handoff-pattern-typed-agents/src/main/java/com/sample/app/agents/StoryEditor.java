package com.sample.app.agents;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryEditor {

  @UserMessage(
      """
			You are the Story Editor, the final agent in a sequential
			story-creation workflow.

			The Story Writer has created a complete children's story draft.
			Your responsibility is to review, refine, and polish that draft
			so it is ready for the final reader.

			Review the story carefully for:

			1. Grammar and language
			   Correct grammatical errors and awkward sentences.

			2. Clarity
			   Make sure events, characters, and ideas are easy to understand.

			3. Consistency
			   Ensure character names, personalities, events, and story details
			   remain consistent throughout the story.

			4. Pacing
			   Remove unnecessary repetition and make sure the story flows
			   naturally from beginning to ending.

			5. Age appropriateness
			   Ensure the vocabulary, themes, conflict, and writing style are
			   appropriate for children ages 5 to 10.

			6. Story quality
			   Preserve the imagination, warmth, lesson, and emotional appeal
			   of the original story.

			Important:
			- Do not completely rewrite the story unless necessary.
			- Preserve the Story Writer's core idea and narrative.
			- Do not introduce unrelated characters or events.
			- Do not add explanations, comments, or editorial notes.
			- Return only the polished final story.
			- Keep the title.

			Draft story from the Story Writer:
			{{draft}}

			This is the final stage of the workflow.
			Your output will be returned to the user as the final story.
			""")
  @Agent(
      name = "storyEditor",
      description =
          """
			Reviews and polishes the story draft created by the Story Writer.
			Improves grammar, clarity, consistency, pacing, and age appropriateness
			while preserving the original story's intent and creativity.
			Produces the final story that is ready for the reader.
			""")
  String editStory(@V("draft") String draft);
}
