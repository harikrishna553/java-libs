package com.sample.app;

import com.sample.app.assistants.ChatAssistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.ArrayList;
import java.util.List;

public class InMemoryRagWithContentRetriever {

  public static void main(String[] args) {

    // Initialize local embedding model
    EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

    // Load all the Documents
    String resourcesFolderPath = "/Users/Shared/llm_docs";
    System.out.println("Resources Folder Path is " + resourcesFolderPath);
    List<Document> documents = FileSystemDocumentLoader.loadDocuments(resourcesFolderPath);

    InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
    EmbeddingStoreIngestor.ingest(documents, embeddingStore);

    OllamaChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    ContentRetriever contentRetriever =
        EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .maxResults(5)
            .minScore(0.75)
            .build();

    ChatAssistant assistant =
        AiServices.builder(ChatAssistant.class)
            .chatModel(chatModel)
            .contentRetriever(contentRetriever)
            .build();

    List<String> questionsToAsk = new ArrayList<>();
    questionsToAsk.add("What is the tag line of ChronoCore Industries?");

    long time1 = System.currentTimeMillis();
    for (String question : questionsToAsk) {
      String answer = assistant.chat(question);
      System.out.println("----------------------------------------------------");
      System.out.println("Q: " + question);
      System.out.println("A : " + answer);
      System.out.println("----------------------------------------------------\n");
    }
    long time2 = System.currentTimeMillis();

    System.out.println("Total time taken is " + (time2 - time1));
  }
}
