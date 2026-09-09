package com.sample.app.models;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.time.Duration;

public class Models {

  private static final String BASE_URL = "http://localhost:11434";

  private static final String BASIC_MODEL = "llama3.2";
  private static final String PLANNER_MODEL = "qwen2.5";

  private Models() {}

  public static ChatModel baseModel() {

    return OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(BASIC_MODEL)
        .temperature(0.0)
        .timeout(Duration.ofSeconds(120))
        .logRequests(false)
        .logResponses(false)
        .build();
  }

  public static ChatModel plannerModel() {
    return OllamaChatModel.builder()
        .baseUrl(BASE_URL)
        .modelName(PLANNER_MODEL)
        .temperature(0.0)
        .timeout(Duration.ofSeconds(120))
        .logRequests(false)
        .logResponses(false)
        .build();
  }
}
