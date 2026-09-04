package com.sample.app.utils;


import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public final class OllamaModels {

    private static final String BASE_URL =
            "http://localhost:11434";

    private static final String MODEL_NAME =
            "llama3.2";

    private OllamaModels() {
    }

    public static ChatModel chatModel() {

        return OllamaChatModel.builder()
                .baseUrl(BASE_URL)
                .modelName(MODEL_NAME)
                .temperature(0.1)
                .build();
    }
}