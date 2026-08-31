package com.sample.app.agents;

import com.sample.app.model.StoryContext;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.scope.AgenticScope;

public class StoryContextExtractor {

  @Agent(
      name = "storyContextExtractor",
      description = "Extracts story context and updates the shared workflow state.")
  public static void extract(AgenticScope scope) {

    StoryContext storyContext = (StoryContext) scope.readState("storyContext");

    System.out.println();
    System.out.println("======================================");
    System.out.println("Extracting Story Context");
    System.out.println("======================================");

    if (storyContext == null) {
      System.out.println("StoryContext is not available.");
      return;
    }

    if (storyContext.getTargetAudience() != null && !storyContext.getTargetAudience().isBlank()) {

      scope.writeState("targetAudience", storyContext.getTargetAudience());

      System.out.println("Target Audience : " + storyContext.getTargetAudience());

    } else {

      System.out.println("Target Audience : Not determined");
    }

    if (storyContext.getStyle() != null && !storyContext.getStyle().isBlank()) {

      scope.writeState("style", storyContext.getStyle());

      System.out.println("Style           : " + storyContext.getStyle());

    } else {

      System.out.println("Style           : Not determined");
    }

    System.out.println("======================================");
  }
}
