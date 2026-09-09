package com.sample.app.agents;

import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryReviewerAgent {

  @UserMessage(
      """
            You are a strict but fair story reviewer.

            Review the following story.

            STORY:

            {{story}}

            QUALITY THRESHOLD:

            {{qualityThreshold}} out of 10

            SUPERVISOR GUIDANCE:

            {{supervisorContext}}

            Evaluate the story based on:

            - Story structure
            - Creativity
            - Readability
            - Age appropriateness
            - Character consistency
            - Engagement
            - Pacing
            - Quality of the ending
            - Compliance with supervisor guidance

            Give a quality score from 1 to 10.

            Set:

            thresholdReached = true

            only when:

            score >= qualityThreshold

            Otherwise set:

            thresholdReached = false

            If the threshold is not reached, provide specific,
            actionable feedback explaining what should be improved.

            If the threshold is reached, briefly explain why the
            story meets the expected quality.
            """)
  @Agent(
      """
            Reviews the current story, assigns a quality score from 1 to 10,
            indicates whether the requested quality threshold has been reached,
            and provides improvement feedback
            """)
  StoryReview reviewStory(
      @V("story") String story,
      @V("qualityThreshold") int qualityThreshold,
      @V("supervisorContext") String supervisorContext);
}
