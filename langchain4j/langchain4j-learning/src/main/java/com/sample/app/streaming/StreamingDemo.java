package com.sample.app.streaming;

import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.chat.response.StreamingChatResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingChatModel;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

public class StreamingDemo {

  public static void main(String[] args) throws InterruptedException {

    OllamaStreamingChatModel chatModel =
        OllamaStreamingChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3.2")
            .temperature(0.3)
            .timeout(Duration.ofMinutes(1))
            .build();

    String prompt = "Tell me some Interesting Fact About LLMs in maximum 30 words";

    chatModel.chat(
        prompt,
        new StreamingChatResponseHandler() {

          @Override
          public void onPartialResponse(String partialResponse) {
            System.out.println("onPartialResponse: " + partialResponse);
          }

          @Override
          public void onCompleteResponse(ChatResponse completeResponse) {
            System.out.println("onCompleteResponse: " + completeResponse);
          }

          @Override
          public void onError(Throwable error) {
            error.printStackTrace();
          }
        });

    // Sleep for 2 minutes to get the complete response tokens
    TimeUnit.MINUTES.sleep(2);
  }
}
