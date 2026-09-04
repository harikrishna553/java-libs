package com.sample.app.agents;

import com.sample.app.config.OllamaModels;
import com.sample.app.util.EmbeddingStoreUtil;
import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.agentic.declarative.ContentRetrieverSupplier;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import java.util.List;

public interface CompanyPolicyAgent {

  @SystemMessage(
      """
            You are a company policy assistant.

            Answer the user's question using the retrieved company
            policy information.

            If the retrieved information does not contain the answer,
            clearly say that the information is not available.
            """)
  @UserMessage(
      """
            {{question}}
            """)
  @Agent(
      name = "CompanyPolicyAgent",
      outputKey = "answer",
      description = "Answers employee questions using company policy content")
  String ask(@V("question") String question);

  @ContentRetrieverSupplier
  static ContentRetriever contentRetriever() {

    System.out.println("CompanyPolicyAgent -> Creating ContentRetriever");

    return query -> {
      System.out.println("ContentRetriever -> Query: " + query.text());

      List<TextSegment> segments = EmbeddingStoreUtil.findMatchingSegments(query.text());

      return segments.stream().map(Content::from).toList();
    };
  }

  @ChatModelSupplier
  static ChatModel chatModel() {

    return OllamaModels.fastModel();
  }
}
