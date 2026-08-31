package com.sample.app.config;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public final class OllamaConfig {

  public static final String DEFAULT_BASE_URL = "http://localhost:11434";
  public static final String DEFAULT_MODEL_NAME = "codellama:7b";
  private static ChatModel chatModel = null;

  private OllamaConfig() {
  }

  static {
    chatModel = initChatModel();
  }

  public static ChatModel getChatModel() {
    return chatModel;
  }

  private static ChatModel initChatModel() {
    String baseUrl = resolveBaseUrl();
    String modelName = resolveModelName();

    return OllamaChatModel.builder().baseUrl(baseUrl).modelName(modelName).temperature(0.7).build();
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
