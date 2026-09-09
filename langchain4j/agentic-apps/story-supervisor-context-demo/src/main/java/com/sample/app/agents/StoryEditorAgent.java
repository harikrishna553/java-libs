package com.sample.app.agents;

import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryEditorAgent {

  @UserMessage(
      """
            You are an expert story editor.

            Improve the current story using the reviewer's feedback.

            CURRENT STORY:

            {{story}}

            LATEST REVIEW:

            {{review}}

            TARGET QUALITY:

            {{qualityThreshold}} out of 10

            SUPERVISOR GUIDANCE:

            {{supervisorContext}}

            Editing requirements:

            - Address the reviewer's specific feedback.
            - Preserve the core story idea.
            - Do not unnecessarily rewrite parts that are already strong.
            - Improve readability, pacing, structure, and engagement.
            - Keep characters and events consistent.
            - Follow all supervisor guidance.
            - Return only the complete improved story.
            - Do not include explanations or editing notes.
            """)
  @Agent(
      """
            Improves the current story based on the latest reviewer feedback.
            Use this agent when the reviewer's quality threshold has not been reached.
            """)
  String editStory(
      @V("story") String story,
      @V("review") StoryReview review,
      @V("qualityThreshold") int qualityThreshold,
      @V("supervisorContext") String supervisorContext);
}
