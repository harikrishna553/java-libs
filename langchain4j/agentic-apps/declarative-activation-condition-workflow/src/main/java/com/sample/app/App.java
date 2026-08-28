package com.sample.app;

import com.sample.app.agents.ContentCreationWorkflow;
import com.sample.app.config.OllamaConfig;
import dev.langchain4j.agentic.AgenticServices;
import java.util.Scanner;

public class App {

  public static void main(String[] args) {

    System.out.println(
        """
		        ==================================================
		                 AI Content Creation Assistant
		        ==================================================

		        Enter a content creation request.

		        You can ask the system to create a:

		        1. Blog
		           Example:
		           Write a blog about how Generative AI is changing software testing.

		        2. Social Media Post
		           Example:
		           Create a LinkedIn post about the benefits of AI agents.

		        3. Email
		           Example:
		           Write an email inviting the QA team to an AI testing workshop.

		        --------------------------------------------------
		        Enter your request:
		        """);

    String userRequest = null;
    try (Scanner scanner = new Scanner(System.in)) {
      userRequest = scanner.nextLine().trim();
    }

    ContentCreationWorkflow workflow =
        AgenticServices.createAgenticSystem(
            ContentCreationWorkflow.class, OllamaConfig.getChatModel());

    String content = workflow.createContent(userRequest);

    System.out.println();
    System.out.println("Generated Content");
    System.out.println("=================");
    System.out.println(content);
  }
}
