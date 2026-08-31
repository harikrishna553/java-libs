package com.sample.app.agents;

import com.sample.app.config.OllamaConfig;
import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryReviewer {

  @UserMessage(
      """
        You are the Story Reviewer in an iterative children's story
        improvement workflow.

        Review the provided story carefully and evaluate its overall
        quality.

        Evaluate the story based on:

        1. Grammar and language
           Is the language clear and grammatically correct?

        2. Clarity
           Are the characters, events, and ideas easy for children
           to understand?

        3. Consistency
           Are characters, events, and story details consistent?

        4. Pacing
           Does the story flow naturally without unnecessary repetition?

        5. Age appropriateness
           Is the vocabulary, theme, and content appropriate for
           children ages 5 to 10?

        6. Story quality
           Is the story interesting, imaginative, and emotionally engaging?

        7. Lesson
           Is the intended message or lesson clear?

        Give the story an overall quality rating from 1 to 10.

        Scoring guidelines:
        - 1-4: Major improvements required
        - 5-6: Several improvements required
        - 7: Good but needs improvement
        - 8: Very good
        - 9: Excellent
        - 10: Outstanding

        Also provide concise, actionable feedback that the Story Improver
        can use to improve the story.

        Important:
        - Do not rewrite the story.
        - Do not provide unnecessary explanations.
        - Identify the most important improvements.
        - Focus the feedback on actionable changes.
        - Return the rating and feedback in the requested structured format.

        Current story:
        {{story}}
        """)
  @Agent(
      name = "storyReviewer",
      description =
          """
            Evaluates the current children's story for grammar, clarity,
            consistency, pacing, age appropriateness, creativity, and
            overall quality.

            Returns a quality rating and actionable feedback that can be
            used by the Story Improver.
            """,
      outputKey = "storyReview")
  StoryReview reviewStory(@V("story") String story);

  @ChatModelSupplier
  static ChatModel chatModel() {
    return OllamaConfig.getChatModel();
  }
}
