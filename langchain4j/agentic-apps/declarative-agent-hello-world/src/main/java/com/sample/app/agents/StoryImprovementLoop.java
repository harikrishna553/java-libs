package com.sample.app.agents;

import com.sample.app.model.StoryReview;
import dev.langchain4j.agentic.declarative.ExitCondition;
import dev.langchain4j.agentic.declarative.LoopAgent;
import dev.langchain4j.agentic.declarative.LoopCounter;
import dev.langchain4j.service.V;

public interface StoryImprovementLoop {

  @LoopAgent(
      name = "storyImprovementLoop",
      outputKey = "story",
      maxIterations = 5,
      subAgents = {StoryReviewer.class, StoryImprover.class})
  String improve(@V("story") String story);

  @ExitCondition(
      testExitAtLoopEnd = true,
      description = "Stop when the story reaches the required quality score")
  static boolean exitCondition(
      @V("storyReview") StoryReview storyReview, @LoopCounter int loopCounter) {

    double score = storyReview != null ? storyReview.getRating() : 0;

    return loopCounter <= 3 ? score >= 9 : score >= 7;
  }
}
