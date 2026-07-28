package com.sample.app.llm;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import java.time.Duration;

/**
 * Thin wrapper around the LangChain4j {@link ChatLanguageModel} for Ollama.
 *
 * <p>This class is the only component that communicates directly with the LLM. It intentionally
 * exposes a minimal surface: one method that accepts a prompt string and returns the raw text
 * response. All prompt construction and response parsing happen in separate classes ({@link
 * PromptBuilder} and {@link PlanParser}).
 *
 * <p><strong>Design note:</strong> We use {@code ChatLanguageModel.generate(String)} directly
 * rather than LangChain4j's {@code AiServices} DSL. This makes the prompt-build → LLM-call → parse
 * pipeline explicit and visible — which is the educational point of this project.
 */
public class LlmClient {

  /** Default Ollama server URL. */
  private static final String DEFAULT_BASE_URL = "http://localhost:11434";

  /** Default model to use. Must be pulled in Ollama before running. */
  private static final String DEFAULT_MODEL = "llama3.2";

  private final ChatLanguageModel chatModel;

  /**
   * Constructs an LlmClient connecting to the default local Ollama instance. Uses model {@value
   * #DEFAULT_MODEL}.
   */
  public LlmClient() {
    this(DEFAULT_BASE_URL, DEFAULT_MODEL);
  }

  /**
   * Constructs an LlmClient with a custom base URL and model name.
   *
   * @param baseUrl Ollama server base URL (e.g., {@code "http://localhost:11434"})
   * @param modelName Ollama model name (e.g., {@code "llama3.2"})
   */
  public LlmClient(String baseUrl, String modelName) {
    this.chatModel =
        OllamaChatModel.builder()
            .baseUrl(baseUrl)
            .modelName(modelName)
            .temperature(0.1) // Low temperature = more deterministic JSON output
            .timeout(Duration.ofSeconds(120))
            .build();
  }

  /**
   * Sends a raw prompt to the LLM and returns the raw text response.
   *
   * <p>The caller is responsible for building the prompt ({@link PromptBuilder}) and parsing the
   * response ({@link PlanParser}). This method performs no transformation on either input or
   * output.
   *
   * @param prompt the full prompt to send to the model
   * @return the model's raw text response
   */
  public String generate(String prompt) {
    return chatModel.chat(prompt);
  }
}
