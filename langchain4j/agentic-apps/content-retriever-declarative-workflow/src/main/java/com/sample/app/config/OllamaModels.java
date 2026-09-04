package com.sample.app.config;

import dev.langchain4j.model.chat.Capability;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.time.Duration;

public final class OllamaModels {

  private static final String BASE_URL = "http://localhost:11434";

  public static final String FAST_MODEL_NAME = "llama3.2";

  public static final String QUALITY_MODEL_NAME = "mistral";

  private static final ChatModel FAST_STRUCTURED_MODEL =
      OllamaChatModel.builder()
          .baseUrl(BASE_URL)
          .modelName(FAST_MODEL_NAME)
          .temperature(0.2)
          .timeout(Duration.ofMinutes(2))
          .supportedCapabilities(Capability.RESPONSE_FORMAT_JSON_SCHEMA)
          .build();

  private static final ChatModel FAST_MODEL =
      OllamaChatModel.builder()
          .baseUrl(BASE_URL)
          .modelName(FAST_MODEL_NAME)
          .temperature(0.2)
          .timeout(Duration.ofMinutes(2))
          .build();

  private static final ChatModel QUALITY_MODEL =
      OllamaChatModel.builder()
          .baseUrl(BASE_URL)
          .modelName(QUALITY_MODEL_NAME)
          .temperature(0.2)
          .timeout(Duration.ofMinutes(2))
          .build();

  private OllamaModels() {}

  public static ChatModel fastModel() {
    return FAST_MODEL;
  }

  public static ChatModel fastStructuredModel() {
    return FAST_STRUCTURED_MODEL;
  }

  public static ChatModel qualityModel() {
    return QUALITY_MODEL;
  }
}
