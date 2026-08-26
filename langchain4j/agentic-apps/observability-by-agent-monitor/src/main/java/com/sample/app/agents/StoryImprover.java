package com.sample.app.agents;

import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryImprover {

  @UserMessage(
      """
        You are the Story Improver in an iterative children's story
        improvement workflow.

        The Story Reviewer has evaluated the current story and provided
        a quality rating and feedback.

        Your responsibility is to improve the story based on that feedback.

        Improve the story for:

        1. Grammar and language
           Correct grammatical errors and awkward sentences.

        2. Clarity
           Make characters, events, and ideas easier to understand.

        3. Consistency
           Fix inconsistencies in character names, personalities,
           events, or story details.

        4. Pacing
           Remove unnecessary repetition and improve the story flow.

        5. Age appropriateness
           Ensure the vocabulary, themes, and writing style are suitable
           for children ages 5 to 10.

        6. Story quality
           Make the story more engaging and imaginative while preserving
           its original intent.

        7. Lesson
           Make the story's message clear without making it feel forced.

        Important:
        - Preserve the original story's core idea.
        - Do not introduce unrelated characters or events.
        - Use the review feedback to guide your improvements.
        - Do not make changes that are unrelated to the reviewer feedback.
        - Improve the existing story rather than completely replacing it.
        - Keep the title.
        - Return ONLY the improved story.
        - Do not include explanations, comments, or editorial notes.

        Current story:
        {{story}}

        Reviewer assessment:
        {{storyReview}}
        """)
  @Agent(
      name = "storyImprover",
      description =
          """
            Improves an existing children's story using the quality rating
            and actionable feedback provided by the Story Reviewer.

            Preserves the original story's intent while addressing the
            identified quality issues and producing an improved version
            for the next review iteration.
            """)
  String improveStory(@V("story") String story, @V("storyReview") StoryReview review);
}
