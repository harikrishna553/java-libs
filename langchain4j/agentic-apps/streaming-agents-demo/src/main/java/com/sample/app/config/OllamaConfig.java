package com.sample.app.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;

public final class OllamaConfig {

  public static final String DEFAULT_BASE_URL = "http://localhost:11434";
  public static final String DEFAULT_MODEL_NAME = "llama3.2";

  private OllamaConfig() {}

  public static ChatModel createChatModel() {
    String baseUrl = resolveBaseUrl();
    String modelName = resolveModelName();

    return OllamaChatModel.builder().baseUrl(baseUrl).modelName(modelName).temperature(0.2).build();
  }

  public static ChatModel createStructuredChatModel() {
    String baseUrl = resolveBaseUrl();
    String modelName = resolveModelName();

    return OllamaChatModel.builder()
        .baseUrl(baseUrl)
        .modelName(modelName)
        .temperature(0.2)
        .responseFormat(ResponseFormat.JSON)
        .build();
  }

  public static StreamingChatModel createStreamingChatModel() {
    String baseUrl = resolveBaseUrl();
    String modelName = resolveModelName();

    StreamingChatModel streamingChatModel =
        OllamaStreamingChatModel.builder()
            .baseUrl(baseUrl)
            .modelName(modelName)
            .temperature(0.2)
            .build();

    return streamingChatModel;
  }

  public static String resolveBaseUrl() {
    return readSetting("OLLAMA_BASE_URL", DEFAULT_BASE_URL);
  }

  public static String resolveModelName() {
    return readSetting("OLLAMA_MODEL", DEFAULT_MODEL_NAME);
  }

  private static String readSetting(String key, String defaultValue) {
    String systemValue = System.getProperty(key);
    if (systemValue != null && !systemValue.trim().isEmpty()) {
      return systemValue.trim();
    }

    String environmentValue = System.getenv(key);
    if (environmentValue != null && !environmentValue.trim().isEmpty()) {
      return environmentValue.trim();
    }

    return defaultValue;
  }
}
