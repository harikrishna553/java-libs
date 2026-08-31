package com.sample.app.agents;

import dev.langchain4j.agentic.agent.ErrorContext;
import dev.langchain4j.agentic.agent.ErrorRecoveryResult;
import dev.langchain4j.agentic.agent.MissingArgumentException;
import dev.langchain4j.agentic.declarative.AgentListenerSupplier;
import dev.langchain4j.agentic.declarative.ErrorHandler;
import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.agentic.observability.AgentListener;
import dev.langchain4j.service.V;

public interface StoryCreationWorkflow {

  @SequenceAgent(
      name = "storyCreationWorkflow",
      outputKey = "story",
      subAgents = {StoryContextAnalyzer.class, StoryContextExtractor.class, StoryWriter.class})
  String createStory(@V("storyIdea") String storyIdea);

  @ErrorHandler
  static ErrorRecoveryResult handleError(ErrorContext errorContext) {

    System.out.println();
    System.out.println("======================================");
    System.out.println("@ErrorHandler invoked");
    System.out.println("======================================");

    System.out.println("Failed Agent : " + errorContext.agentName());

    System.out.println("Exception    : " + errorContext.exception().getMessage());

    if (errorContext.agentName().equals("storyWriter")
        && errorContext.exception() instanceof MissingArgumentException missingArgument
        && missingArgument.argumentName().equals("targetAudience")) {

      System.out.println();
      System.out.println("targetAudience is missing.");

      System.out.println("Applying default targetAudience = KIDS");

      errorContext.agenticScope().writeState("targetAudience", "KIDS");

      System.out.println("Retrying StoryWriter...");

      System.out.println("======================================");

      return ErrorRecoveryResult.retry();
    }

    if (errorContext.agentName().equals("storyWriter")
        && errorContext.exception() instanceof MissingArgumentException missingArgument
        && missingArgument.argumentName().equals("style")) {

      System.out.println();
      System.out.println("style is missing.");

      System.out.println("Applying default style = CREATIVE");

      errorContext.agenticScope().writeState("style", "CREATIVE");

      System.out.println("Retrying StoryWriter...");

      System.out.println("======================================");

      return ErrorRecoveryResult.retry();
    }

    System.out.println("Unable to recover from the error.");

    System.out.println("Propagating exception...");

    return ErrorRecoveryResult.throwException();
  }

  @AgentListenerSupplier
  static AgentListener listener() {
    return new AgentWorkflowListener();
  }
}
