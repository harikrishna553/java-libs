package com.sample.app.chatmodels;

import dev.langchain4j.data.message.ImageContent;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class DescribeImage {

  public static void main(String[] args) {
    // Create the Ollama language model
    ChatModel chatModel =
        OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3.2-vision")
            .build();

    UserMessage userMessage =
        UserMessage.from(
            TextContent.from("Describe the following image"),
            ImageContent.from(
                "https://raw.githubusercontent.com/yavuzceliker/sample-images/main/images/image-1.jpg"));
    ChatResponse response = chatModel.chat(userMessage);

    // Print the AI response
    System.out.println("AI Response: " + response.aiMessage().text());
  }
}
