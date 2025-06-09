package com.sample.app.chatmodels;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.TokenWindowChatMemory;
import dev.langchain4j.model.TokenCountEstimator;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.util.Scanner;

public class ChatMemoryDemo {

  public static void main(String[] args) {
    // Build the Ollama language model
    ChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    // Use the model itself as the token count estimator
    TokenCountEstimator estimator = new SimpleTokenCountEstimator();

    // Create chat memory with token-based eviction policy
    ChatMemory memory = TokenWindowChatMemory.builder().maxTokens(1000, estimator).build();

    Scanner scanner = new Scanner(System.in);
    System.out.println("Chat with AI. Type 'exit' to quit.");

    while (true) {
      System.out.print("\nYou: ");
      String input = scanner.nextLine();

      if ("exit".equalsIgnoreCase(input.trim())) {
        System.out.println("Conversation ended.");
        break;
      }

      UserMessage userMessage = UserMessage.from(input);
      memory.add(userMessage);

      AiMessage aiMessage = chatModel.chat(memory.messages()).aiMessage();
      memory.add(aiMessage);

      System.out.println("AI: " + aiMessage.text());
    }

    scanner.close();
  }
}
