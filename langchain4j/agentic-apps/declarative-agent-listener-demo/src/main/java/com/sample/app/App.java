package com.sample.app;

import com.sample.app.agents.StoryWorkflow;
import com.sample.app.config.OllamaConfig;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {

  private static void printIntroduction() {

    System.out.println("==========================================");
    System.out.println("       AI Story Creation Agent");
    System.out.println("==========================================");
    System.out.println();
    System.out.println("This agentic workflow will:");
    System.out.println("  1. Generate a story from your idea.");
    System.out.println("  2. Review the generated story.");
    System.out.println("  3. Return the final reviewed story.");
    System.out.println();
    System.out.println("Agent execution is monitored using");
    System.out.println("@AgentListenerSupplier and AgentListener.");
    System.out.println();
    System.out.println("Example story ideas:");
    System.out.println("  - A robot discovers an ancient forest");
    System.out.println("  - A child finds a magical key");
    System.out.println("  - An astronaut discovers life on Mars");
    System.out.println();
    System.out.print("Enter your story idea: ");
  }

  public static void main(String[] args) {

    ChatModel chatModel = OllamaConfig.getChatModel();

    printIntroduction();

    String storyIdea;

    try (Scanner scanner = new Scanner(System.in)) {
      storyIdea = scanner.nextLine().trim();
    }

    StoryWorkflow storyWorkflow =
        AgenticServices.createAgenticSystem(StoryWorkflow.class, chatModel);

    String finalStory = storyWorkflow.createStory(storyIdea);

    System.out.println();
    System.out.println("==========================================");
    System.out.println("              Final Story");
    System.out.println("==========================================");
    System.out.println();

    System.out.println(finalStory);
  }
}
