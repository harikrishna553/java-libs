package com.sample.app;

import com.sample.app.agents.AudienceEditor;
import com.sample.app.agents.CreativeWriter;
import com.sample.app.agents.StoryRequestAnalyzer;
import com.sample.app.agents.StoryWorkflow;
import com.sample.app.agents.StyleEditor;
import com.sample.app.config.OllamaConfig;
import com.sample.app.model.StoryRequest;
import dev.langchain4j.agentic.AgenticServices;
import dev.langchain4j.agentic.AgenticServices.AgenticScopeAction;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.TokenStream;
import java.util.Scanner;
import java.util.concurrent.CountDownLatch;

public class App {

  private static String readInput(Scanner scanner) {
    System.out.print("> Could you please tell me what kind of story you would like me to write?\n");

    String input = scanner.nextLine().trim();

    if ("exit".equalsIgnoreCase(input)) {
      System.exit(0);
    }
    return input;
  }

  public static void main(String[] args) {

    ChatModel chatModel = OllamaConfig.createChatModel();
    StreamingChatModel streamingChatmodel = OllamaConfig.createStreamingChatModel();

    StoryRequestAnalyzer storyRequestAnalyzer =
        AgenticServices.agentBuilder(StoryRequestAnalyzer.class)
            .chatModel(chatModel)
            .outputKey("storyRequest")
            .build();

    AgenticScopeAction extractStoryRequest =
        AgenticServices.agentAction(
            scope -> {
              StoryRequest storyRequest = (StoryRequest) scope.readState("storyRequest");

              if (storyRequest.getTopic() == null || storyRequest.getTopic().isBlank()) {
                System.out.println(
                    "Unable to deduce the topic, setting to " + "'Any Interesting Topic'");

                storyRequest.setTopic("Any Interesting and Engaging Story");
              }

              if (storyRequest.getStyle() == null || storyRequest.getStyle().isBlank()) {
                System.out.println("Unable to deduce the style, setting it to " + "'Creative'");

                storyRequest.setStyle("Creative");
              }

              if (storyRequest.getAudience() == null || storyRequest.getAudience().isBlank()) {
                storyRequest.setAudience("Kids");
              }

              scope.writeState("topic", storyRequest.getTopic());
              scope.writeState("style", storyRequest.getStyle());
              scope.writeState("audience", storyRequest.getAudience());

              System.out.println("Deduced following data : \n" + storyRequest);
            });

    CreativeWriter creativeWriter =
        AgenticServices.agentBuilder(CreativeWriter.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    AudienceEditor audienceEditor =
        AgenticServices.agentBuilder(AudienceEditor.class)
            .chatModel(chatModel)
            .outputKey("story")
            .build();

    StyleEditor styleEditor =
        AgenticServices.agentBuilder(StyleEditor.class)
            .streamingChatModel(streamingChatmodel)
            .outputKey("story")
            .build();

    StoryWorkflow storyWorkflow =
        AgenticServices.sequenceBuilder(StoryWorkflow.class)
            .subAgents(
                storyRequestAnalyzer,
                extractStoryRequest,
                creativeWriter,
                audienceEditor,
                styleEditor)
            .outputKey("story")
            .build();

    try (Scanner scanner = new Scanner(System.in)) {

      while (true) {

        String input = readInput(scanner);

        TokenStream tokenStream = storyWorkflow.writeStory(input);

        CountDownLatch latch = new CountDownLatch(1);

        tokenStream
            .onPartialResponse(
                token -> {
                  System.out.print(token);
                  System.out.flush();
                })
            .onCompleteResponse(
                response -> {
                  System.out.println();
                  System.out.println();
                  latch.countDown();
                })
            .onError(
                error -> {
                  System.err.println("Error: " + error.getMessage());
                  latch.countDown();
                })
            .start();

        try {
          latch.await();
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          break;
        }
      }
    }
  }
}
