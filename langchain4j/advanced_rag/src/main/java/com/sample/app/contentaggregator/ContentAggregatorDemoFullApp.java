package com.sample.app.contentaggregator;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.aggregator.DefaultContentAggregator;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.router.LanguageModelQueryRouter;
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContentAggregatorDemoFullApp {
  interface ChatAssistant {
    String chat(String userMessage);
  }

  public static void main(String[] args) {

    // Initialize LLM model for routing and chatting
    OllamaChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    // Shared embedding model
    EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

    // Create embedding stores
    EmbeddingStore<TextSegment> hrStore = new InMemoryEmbeddingStore<>();
    EmbeddingStore<TextSegment> itStore = new InMemoryEmbeddingStore<>();
    EmbeddingStore<TextSegment> financeStore = new InMemoryEmbeddingStore<>();

    // Sample data for ingestion

    List<String> hrDocs =
        List.of(
            "Our HR policies include flexible work hours, medical leave, and onboarding support.",
            "You can check your leave balance and benefits on the HR portal.",
            "Employee handbooks and code of conduct documents are available on the intranet.",
            "Annual performance reviews are conducted in Q1 of each year.",
            "We provide mental wellness programs and employee assistance plans (EAP).",
            "New employees must complete their joining formalities within the first week.",
            "HR holds town halls every quarter to discuss policy changes and updates.",
            "Exit interviews are mandatory and help improve employee retention practices.");

    List<String> itDocs =
        List.of(
            "If you're facing issues with VPN, restart your system and try again.",
            "Password resets can be done via the IT Helpdesk portal.",
            "For software installation requests, raise a ticket through the ServiceNow portal.",
            "Two-factor authentication is mandatory for accessing company email remotely.",
            "Laptop issues should be reported to the IT asset management team.",
            "New joiners will receive their device credentials within 24 hours of onboarding.",
            "We recommend using the Chrome browser for all internal web tools.",
            "The weekly IT newsletter includes patch updates and known issues.");

    List<String> financeDocs =
        List.of(
            "Payslips are generated on the 5th of every month and available on the finance dashboard.",
            "You can file business travel reimbursements through the expense portal.",
            "Employees must submit receipts within 15 days for expense claims.",
            "Annual tax declarations must be uploaded to the HRMS by January 31.",
            "Salary revisions are processed in March and reflected in April pay.",
            "All invoice-related queries should be addressed to finance@company.com.",
            "The company reimburses professional certification exam fees up to ₹10,000.",
            "You can track investment proofs under the ‘My Tax’ section on the intranet.");

    // Ingest documents into respective stores
    EmbeddingStoreIngestor.ingest(convertToDocuments(hrDocs), hrStore);
    EmbeddingStoreIngestor.ingest(convertToDocuments(itDocs), itStore);
    EmbeddingStoreIngestor.ingest(convertToDocuments(financeDocs), financeStore);

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

    // Expand queries (optional step for better retrieval)
    ExpandingQueryTransformer expandingQueryTransformer =
        new ExpandingQueryTransformer(chatModel, 3);

    // Create retrieval augmentor with dynamic contentRetriever via router
    RetrievalAugmentor retrievalAugmentor =
        DefaultRetrievalAugmentor.builder()
            .queryTransformer(expandingQueryTransformer)
            .queryRouter(router)
            .contentAggregator(new DefaultContentAggregator())
            .build();

    // Build the assistant
    ChatAssistant chatAssistant =
        AiServices.builder(ChatAssistant.class)
            .chatModel(chatModel)
            .retrievalAugmentor(retrievalAugmentor)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .build();

    // Run a conversation
    List<String> userQueries =
        List.of(
            "How do I reset my email password?",
            "Where can I see my payslip for last month?",
            "What is the company policy on medical leave?",
            "How to claim business travel reimbursement?",
            "I'm facing issues connecting to the VPN.");

    for (String userQuery : userQueries) {
      System.out.println("User: " + userQuery);
      String answer = chatAssistant.chat(userQuery);
      System.out.println("Assistant: " + answer);
      System.out.println("-----------------------------------------------");
    }
  }

  private static List<Document> convertToDocuments(List<String> texts) {
    List<Document> documents = new ArrayList<>();
    for (String text : texts) {
      documents.add(Document.from(text));
    }
    return documents;
  }
}
