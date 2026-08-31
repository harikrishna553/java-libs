package com.sample.app;

import com.sample.app.agents.ContentCreationWorkflow;
import com.sample.app.config.OllamaConfig;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    System.out.println(
        """
				==================================================
				         AI Content Creation Workflow
				==================================================

				Enter a content creation request.

				In this example, the workflow uses @BeforeCall
				to initialize the following default values:

				Content Type : BLOG
				Audience     : SOFTWARE_ENGINEERS
				Tone         : PROFESSIONAL
				Review Status: PENDING

				The request will then be processed by:

				1. ContentWriter
				   Creates the initial draft.

				2. ContentReviewer
				   Reviews and improves the generated draft.

				Example Request:

				Write a blog about how Generative AI is changing
				software testing and why QA engineers should learn
				AI testing techniques.

				--------------------------------------------------
				Enter your request:
				""");

    String userRequest = null;
    try (Scanner scanner = new Scanner(System.in)) {
      userRequest = scanner.nextLine().trim();
    }

    ChatModel chatModel = OllamaConfig.getChatModel();

    ContentCreationWorkflow workflow =
        AgenticServices.createAgenticSystem(ContentCreationWorkflow.class, chatModel);

    String content = workflow.createContent(userRequest);

    System.out.println();
    System.out.println("========== FINAL CONTENT ==========");
    System.out.println(content);
  }
}
