package com.sample.app.chatmodels;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.ChatResponseMetadata;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.FinishReason;
import dev.langchain4j.model.output.TokenUsage;
import java.util.List;

public class ChatMessagesDemo {

  public static void main(String[] args) {

    // Create the Ollama language model
    ChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();
    UserMessage userMessage = new UserMessage("Hi");

    ChatResponse response = chatModel.chat(userMessage);

    AiMessage aiMessage = response.aiMessage();
    String responseFromLLM = aiMessage.text();
    List<ToolExecutionRequest> toolsToExecute = aiMessage.toolExecutionRequests();

    System.out.println("responseFromLLM : " + responseFromLLM);
    for (ToolExecutionRequest toolExecutionRequest : toolsToExecute) {
      System.out.println(
          toolExecutionRequest.id()
              + " "
              + toolExecutionRequest.name()
              + " "
              + toolExecutionRequest.arguments());
    }

    ChatResponseMetadata chatResponseMetadata = response.metadata();
    FinishReason finishReason = chatResponseMetadata.finishReason();
    String modelName = chatResponseMetadata.modelName();
    TokenUsage tokenUsage = chatResponseMetadata.tokenUsage();

    System.out.println("The reason why a model call finished : " + finishReason);
    System.out.println("modelName : " + modelName);
    System.out.println("inputTokenCount : " + tokenUsage.inputTokenCount());
    System.out.println("outputTokenCount : " + tokenUsage.outputTokenCount());
    System.out.println("totalTokenCount : " + tokenUsage.totalTokenCount());
  }
}
