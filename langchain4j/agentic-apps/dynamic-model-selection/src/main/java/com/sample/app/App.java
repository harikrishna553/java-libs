package com.sample.app;

import com.sample.app.agents.ChatAssistant;
import com.sample.app.agents.ModelSelectionAgent;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.ModelType;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.UntypedAgent;
import java.util.Map;
import java.util.Scanner;

public class App {

  private static String readInput(Scanner scanner) {
    System.out.print("> Ask me Anything or type exit to terminate the Application\n");

    String input = scanner.nextLine().trim();

    if ("exit".equalsIgnoreCase(input)) {
      System.exit(0);
    }
    return input;
  }

  public static void main(String[] args) {
    ModelSelectionAgent modelSelectionAgent =
        AgenticServices.agentBuilder(ModelSelectionAgent.class)
            .chatModel(OllamaConfig.createChatModel())
            .outputKey("chatModel")
            .build();

    ChatAssistant chatAssistant =
        AgenticServices.agentBuilder(ChatAssistant.class)
            .chatModel(
                scope -> {
                  ModelType modelType = scope.readState("chatModel", ModelType.GENERAL);

                  if (ModelType.GENERAL == modelType) {
                    System.out.println("Default Model is used");
                    return OllamaConfig.createChatModel();
                  }
                  System.out.println("Codellama is used");
                  return OllamaConfig.createChatModel(OllamaConfig.CODE_LLAMA_MODEL);
                })
            .outputKey("response")
            .build();

    UntypedAgent novelCreator =
        AgenticServices.sequenceBuilder()
            .subAgents(modelSelectionAgent, chatAssistant)
            .outputKey("response")
            .build();

    try (Scanner scanner = new Scanner(System.in)) {
      while (true) {
        String input = readInput(scanner);
        Map<String, Object> contextMap =
            Map.of("request", "Write a simple Binary Search Tree implementation in Java");

        String response = (String) novelCreator.invoke(contextMap);
        System.out.println(response);
        System.out.println("\n");
      }
    }
  }
}
