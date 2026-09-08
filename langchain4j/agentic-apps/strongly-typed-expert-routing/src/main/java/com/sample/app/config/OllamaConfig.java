package com.sample.app.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.time.Duration;

public final class OllamaConfig {

  private static final String BASE_URL = "http://localhost:11434";

  private static final String MODEL_NAME = "llama3.2";

  private OllamaConfig() {}

  public static ChatModel getChatModel() {

    return OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(MODEL_NAME)
        .temperature(0.0)
        .timeout(Duration.ofSeconds(120))
        .logRequests(false)
        .logResponses(false)
        .build();
  }
}
