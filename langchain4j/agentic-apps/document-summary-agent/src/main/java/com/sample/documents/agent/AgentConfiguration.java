package com.sample.documents.agent;

import static dev.langchain4j.model.chat.Capability.RESPONSE_FORMAT_JSON_SCHEMA;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

/**
 * Creates and configures the AI Agent.
 *
 * <p>This configuration wires the FileSummaryAgent interface with the Ollama Llama 3.2 chat model.
 */
public final class AgentConfiguration {

  private static final String BASE_URL = "http://localhost:11434";
  private static final String MODEL_NAME = "llama3.2";

  private AgentConfiguration() {}

  /**
   * Creates the File Summary AI Agent.
   *
   * @return configured FileSummaryAgent
   */
  public static FileSummaryAgent createFileSummaryAgent() {

    ChatModel chatModel =
        OllamaChatModel.builder()
            .baseUrl(BASE_URL)
            .modelName(MODEL_NAME)
            .supportedCapabilities(RESPONSE_FORMAT_JSON_SCHEMA)
            .temperature(0.2)
            .build();

    return AiServices.create(FileSummaryAgent.class, chatModel);
  }
}
