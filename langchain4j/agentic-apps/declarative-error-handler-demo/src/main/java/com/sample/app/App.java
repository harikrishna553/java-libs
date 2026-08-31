package com.sample.app;

import com.sample.app.agents.StoryCreationWorkflow;
import com.sample.app.config.OllamaConfig;
import dev.langchain4j.agentic.AgenticServices;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    StoryCreationWorkflow workflow =
        AgenticServices.createAgenticSystem(
            StoryCreationWorkflow.class, OllamaConfig.getChatModel());

    System.out.println(
        """
				==================================================
				         AI Story Creation Assistant
				==================================================

				Enter a story idea.

				In this demo, we intentionally do NOT provide
				the targetAudience.

				The StoryWriter requires:

				- storyIdea
				- targetAudience

				When targetAudience is missing:

				StoryWriter
				    ↓
				Error Occurs
				    ↓
				@ErrorHandler
				    ↓
				targetAudience = KIDS
				    ↓
				Retry StoryWriter
				    ↓
				Story Generated

				Example:

				A rabbit discovers a magical forest and helps
				a lost bird find its family.

				--------------------------------------------------
				Enter your story idea:
				""");

    Scanner scanner = new Scanner(System.in);

    String storyIdea = scanner.nextLine();

    String story = workflow.createStory(storyIdea);

    System.out.println();
    System.out.println("======================================");
    System.out.println("Generated Story");
    System.out.println("======================================");

    System.out.println(story);

    scanner.close();
  }
}
