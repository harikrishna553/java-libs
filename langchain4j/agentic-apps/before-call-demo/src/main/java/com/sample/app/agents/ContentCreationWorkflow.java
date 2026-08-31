package com.sample.app.agents;

import dev.langchain4j.agentic.declarative.BeforeCall;
import dev.langchain4j.agentic.declarative.SequenceAgent;
import dev.langchain4j.agentic.scope.AgenticScope;
import dev.langchain4j.service.V;

public interface ContentCreationWorkflow {

  @SequenceAgent(
      name = "contentCreationWorkflow",
      outputKey = "finalContent",
      subAgents = {ContentWriter.class, ContentReviewer.class})
  String createContent(@V("request") String request);

  @BeforeCall
  static void initializeWorkflow(AgenticScope agenticScope) {

    System.out.println("======================================");
    System.out.println("@BeforeCall invoked");
    System.out.println("Initializing Content Creation Workflow");
    System.out.println("======================================");

    agenticScope.writeStateIfAbsent("contentType", "BLOG");

    agenticScope.writeStateIfAbsent("audience", "SOFTWARE_ENGINEERS");

    agenticScope.writeStateIfAbsent("tone", "PROFESSIONAL");

    agenticScope.writeStateIfAbsent("reviewStatus", "PENDING");

    System.out.println("Content Type : " + agenticScope.readState("contentType"));

    System.out.println("Audience     : " + agenticScope.readState("audience"));

    System.out.println("Tone         : " + agenticScope.readState("tone"));

    System.out.println("Review Status: " + agenticScope.readState("reviewStatus"));

    System.out.println("======================================");
  }
}
