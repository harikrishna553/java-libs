package com.sample.app.embeddings;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.output.TokenUsage;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.IngestionResult;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.ArrayList;
import java.util.List;

public class EmbeddingStoreIngestorDemo {

  public interface ChatAssistant {
    String chat(String userMessage);
  }

  public static void main(String[] args) {

    // Load all the Documents
    List<Document> documents = new ArrayList<>();
    Document doc1 =
        Document.from("The stock market surged as tech companies reported strong earnings.");
    Document doc2 =
        Document.from("Tech giants like Apple and Amazon saw record profits this quarter.");
    Document doc3 = Document.from("Heavy rain caused flooding in coastal towns yesterday.");
    documents.add(doc1);
    documents.add(doc2);
    documents.add(doc3);

    InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

    EmbeddingStoreIngestor ingestor =
        EmbeddingStoreIngestor.builder()
            .embeddingModel(new BgeSmallEnV15QuantizedEmbeddingModel())
            .embeddingStore(embeddingStore)
            .build();
    IngestionResult ingestionResult = ingestor.ingest(documents);
    TokenUsage tokenUsage = ingestionResult.tokenUsage();
    System.out.println("Input Token Count : " + tokenUsage.inputTokenCount());
    System.out.println("Output Token Count : " + tokenUsage.outputTokenCount());
    System.out.println("Total Token Count : " + tokenUsage.totalTokenCount());

    OllamaChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    ChatAssistant assistant =
        AiServices.builder(ChatAssistant.class)
            .chatModel(chatModel)
            .contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore))
            .build();

    List<String> questionsToAsk = new ArrayList<>();
    questionsToAsk.add("Is tech companies stocks surged?");

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
