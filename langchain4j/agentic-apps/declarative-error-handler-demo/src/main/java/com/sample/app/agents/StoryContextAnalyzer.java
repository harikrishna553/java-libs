package com.sample.app.agents;

import com.sample.app.model.StoryContext;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface StoryContextAnalyzer {

  @UserMessage(
      """
        Analyze the following story idea and determine
        the most appropriate story context.

        Story Idea:
        {{storyIdea}}

        Determine the following:

        1. Target Audience

        Choose one of:
        - KIDS
        - TEENS
        - ADULTS

        Consider:
        - complexity of the story
        - subject matter
        - themes
        - language likely to be appropriate

        2. Story Style

        Choose one of:
        - ADVENTURE
        - FANTASY
        - COMEDY
        - MYSTERY
        - EDUCATIONAL
        - DRAMA

        Consider:
        - story setting
        - characters
        - events
        - overall theme
        - intended storytelling experience

        IMPORTANT:

        Determine each value only when there is enough
        information in the story idea to do so confidently.

        If the target audience cannot be confidently determined,
        set targetAudience to null.

        If the story style cannot be confidently determined,
        set style to null.

        Do not guess.

        Do not use values such as UNKNOWN or UNDEFINED.
        """)
  @Agent(
      name = "storyContextAnalyzer",
      description = "Analyzes the story idea and determines the target audience and story style.",
      outputKey = "storyContext")
  StoryContext determineContext(@V("storyIdea") String storyIdea);
}
