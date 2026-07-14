package com.example.storyteller;

import com.example.storyteller.agent.StoryTeller;
import com.example.storyteller.console.ConsoleRenderer;
import dev.langchain4j.memory.chat.ChatMemoryProvider;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import java.time.Duration;
import java.util.Scanner;
import java.util.UUID;

public class Application {

  public static void main(String[] args) {

    try {

      // ----------------------------------------------------------
      // Generate Conversation Id
      // ----------------------------------------------------------

      String conversationId = UUID.randomUUID().toString();

      // ----------------------------------------------------------
      // Welcome Banner
      // ----------------------------------------------------------

      ConsoleRenderer.printBanner();
      ConsoleRenderer.info("Conversation : " + conversationId);
      ConsoleRenderer.info("Type 'exit' to quit.");
      System.out.println();

      // ----------------------------------------------------------
      // Configure Ollama
      // ----------------------------------------------------------

      ChatModel model =
          OllamaChatModel.builder()
              .baseUrl("http://localhost:11434")
              .modelName("llama3.2")
              .timeout(Duration.ofMinutes(2))
              .build();

      // ----------------------------------------------------------
      // Shared Memory Store
      // ----------------------------------------------------------

      InMemoryChatMemoryStore memoryStore = new InMemoryChatMemoryStore();

      // ----------------------------------------------------------
      // Memory Provider
      // ----------------------------------------------------------

      ChatMemoryProvider memoryProvider =
          id ->
              MessageWindowChatMemory.builder()
                  .id(id)
                  .maxMessages(20)
                  .chatMemoryStore(memoryStore)
                  .build();

      // ----------------------------------------------------------
      // Build AI Agent
      // ----------------------------------------------------------

      StoryTeller storyTeller =
          AiServices.builder(StoryTeller.class)
              .chatModel(model)
              .chatMemoryProvider(memoryProvider)
              .build();

      // ----------------------------------------------------------
      // Conversation Loop
      // ----------------------------------------------------------

      Scanner scanner = new Scanner(System.in);

      while (true) {

        // Prompt
        ConsoleRenderer.printPrompt();

        String input = scanner.nextLine().trim();

        if (input.isBlank()) {
          continue;
        }

        if ("exit".equalsIgnoreCase(input) || "quit".equalsIgnoreCase(input)) {
          break;
        }

        ConsoleRenderer.thinking();

        String response = storyTeller.chat(conversationId, input);

        ConsoleRenderer.agent(response);
      }

      System.out.println();
      ConsoleRenderer.info("Thanks for using Story Teller! Happy reading 📚✨");

    } catch (Exception ex) {

      ConsoleRenderer.error("Unexpected error");
      ConsoleRenderer.error(ex.getMessage());
    }
  }
}
