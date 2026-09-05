package com.sample.app.utils;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

import java.time.Duration;

public final class OllamaModels {

    public static final String BASE_URL =
            "http://localhost:11434";

    public static final String MODEL_NAME =
            "llama3.2";

    private OllamaModels() {
    }

    public static ChatModel chatModel() {

        return OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .temperature(0.0)
                .timeout(Duration.ofMinutes(2))
                .build();
    }
}