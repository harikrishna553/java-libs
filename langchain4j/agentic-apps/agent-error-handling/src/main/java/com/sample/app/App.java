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
import dev.langchain4j.agentic.agent.ErrorRecoveryResult;
import dev.langchain4j.agentic.agent.MissingArgumentException;
import dev.langchain4j.model.chat.ChatModel;
import java.util.Scanner;

public class App {
  private static final String DEFAULT_TOPIC = "dragons and wizards";
  private static final String DEFAULT_STYLE = "Creative";
  private static final String DEFAULT_AUDIENCE = "Kids";

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

    StoryRequestAnalyzer storyRequestAnalyzer =
        AgenticServices.agentBuilder(StoryRequestAnalyzer.class)
            .chatModel(chatModel)
            .outputKey("storyRequest")
            .build();

    AgenticScopeAction extractStoryRequest =
        AgenticServices.agentAction(
            scope -> {
              StoryRequest storyRequest = (StoryRequest) scope.readState("storyRequest");

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
            .chatModel(chatModel)
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
            .errorHandler(
                errorContext -> {
                  if (errorContext.exception() instanceof MissingArgumentException mEx
                      && mEx.argumentName().equals("topic")) {

                    System.out.println(
                        """

								============================================================
								[ERROR RECOVERY] Missing Story Topic
								------------------------------------------------------------
								The story topic could not be determined.
								Applying default topic: "%s"
								Action: Retrying the workflow...
								============================================================
								"""
                            .formatted(DEFAULT_TOPIC));

                    errorContext.agenticScope().writeState("topic", DEFAULT_TOPIC);
                    return ErrorRecoveryResult.retry();
                  }

                  if (errorContext.exception() instanceof MissingArgumentException mEx
                      && mEx.argumentName().equals("style")) {

                    System.out.println(
                        """

								============================================================
								[ERROR RECOVERY] Missing Writing Style
								------------------------------------------------------------
								The writing style could not be determined.
								Applying default style: "%s"
								Action: Retrying the workflow...
								============================================================
								"""
                            .formatted(DEFAULT_STYLE));

                    errorContext.agenticScope().writeState("style", DEFAULT_STYLE);
                    return ErrorRecoveryResult.retry();
                  }

                  if (errorContext.exception() instanceof MissingArgumentException mEx
                      && mEx.argumentName().equals("audience")) {

                    System.out.println(
                        """

								============================================================
								[ERROR RECOVERY] Missing Target Audience
								------------------------------------------------------------
								The target audience could not be determined.
								Applying default audience: "%s"
								Action: Retrying the workflow...
								============================================================
								"""
                            .formatted(DEFAULT_AUDIENCE));

                    errorContext.agenticScope().writeState("audience", DEFAULT_AUDIENCE);
                    return ErrorRecoveryResult.retry();
                  }

                  System.out.println(
                      """

							!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
							[ERROR] Unexpected Workflow Failure
							------------------------------------------------------------
							The workflow encountered an unexpected error.
							Recovery is not available for this error.
							Action: Propagating the exception...
							!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
							""");

                  return ErrorRecoveryResult.throwException();
                })
            .outputKey("story")
            .build();

    try (Scanner scanner = new Scanner(System.in)) {

      while (true) {

        String input = readInput(scanner);

        String story = storyWorkflow.writeStory(input);

        System.out.println(story);
      }
    }
  }
}
