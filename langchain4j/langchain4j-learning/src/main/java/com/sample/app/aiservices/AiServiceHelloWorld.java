package com.sample.app.aiservices;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

public class AiServiceHelloWorld {
  public static void main(String[] args) {
    ChatModel model =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    MyAssistant assistant = AiServices.create(MyAssistant.class, model);
    String response = assistant.chat("Hi, tell me about LLMs in 30 words maximum");
    System.out.println(response);
  }
}
