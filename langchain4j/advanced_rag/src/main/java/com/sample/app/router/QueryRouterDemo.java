package com.sample.app.router;

import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.router.LanguageModelQueryRouter;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QueryRouterDemo {

  public static void main(String[] args) {

    // Initialize LLM model for routing
    OllamaChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    // Shared embedding model
    EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

    // Create embedding stores
    EmbeddingStore<TextSegment> hrStore = new InMemoryEmbeddingStore<>();
    EmbeddingStore<TextSegment> itStore = new InMemoryEmbeddingStore<>();
    EmbeddingStore<TextSegment> financeStore = new InMemoryEmbeddingStore<>();

    // Create content retrievers
    ContentRetriever hrRetriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(hrStore)
            .embeddingModel(embeddingModel)
            .maxResults(3)
            .minScore(0.7)
            .displayName("hrRetriever")
            .build();

    ContentRetriever itRetriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(itStore)
            .embeddingModel(embeddingModel)
            .maxResults(3)
            .minScore(0.7)
            .displayName("itRetriever")
            .build();

    ContentRetriever financeRetriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(financeStore)
            .embeddingModel(embeddingModel)
            .maxResults(3)
            .minScore(0.7)
            .displayName("financeRetriever")
            .build();

    Map<ContentRetriever, String> retrieverToDescription = new HashMap<>();
    retrieverToDescription.put(
        hrRetriever,
        "Provides information on leave policies, benefits, onboarding, and other HR-related topics.");
    retrieverToDescription.put(
        itRetriever,
        "Handles technical queries such as VPN access, email issues, password resets, and software installations.");
    retrieverToDescription.put(
        financeRetriever,
        "Answers questions related to reimbursements, payslips, taxation, invoices, and financial approvals.");

    // Create LLM-based router
    LanguageModelQueryRouter router =
        LanguageModelQueryRouter.builder()
            .chatModel(chatModel)
            .retrieverToDescription(retrieverToDescription)
            .build();

    // Sample queries
    List<String> queries =
        List.of(
            "How do I reset my email password?",
            "Where can I see my payslip for last month?",
            "What is the company policy on medical leave?",
            "How to claim business travel reimbursement?",
            "I'm facing issues connecting to the VPN.");

    for (String userQuery : queries) {
      Query query = Query.from(userQuery);
      Collection<ContentRetriever> retrievers = router.route(query);

      System.out.println("Query: " + userQuery);
      for (ContentRetriever retriever : retrievers) {
        System.out.println("-> Routed to: " + retriever);
      }
      System.out.println("---------------------------------------------------");
    }
  }
}
