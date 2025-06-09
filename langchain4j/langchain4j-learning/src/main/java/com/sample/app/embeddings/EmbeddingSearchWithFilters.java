package com.sample.app.embeddings;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.filter.Filter;
import dev.langchain4j.store.embedding.filter.comparison.IsEqualTo;
import dev.langchain4j.store.embedding.filter.comparison.IsGreaterThanOrEqualTo;
import dev.langchain4j.store.embedding.filter.comparison.IsIn;
import dev.langchain4j.store.embedding.filter.logical.And;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EmbeddingSearchWithFilters {

  public static void main(String[] args) {

    // Step 1: Initialize embedding model
    EmbeddingModel model = new BgeSmallEnV15QuantizedEmbeddingModel();

    // Step 2: Prepare text data with metadata
    String sentence1 = "The stock market surged as tech companies reported strong earnings.";
    Map<String, Object> metaMap1 = new HashMap<>();
    metaMap1.put("category", "finance");
    metaMap1.put("source", "news");
    metaMap1.put("relevance", 9);
    Metadata meta1 = Metadata.from(metaMap1);

    String sentence2 = "Tech giants like Apple and Amazon saw record profits this quarter.";
    Map<String, Object> metaMap2 = new HashMap<>();
    metaMap2.put("category", "technology");
    metaMap2.put("source", "news");
    metaMap2.put("relevance", 10);
    Metadata meta2 = Metadata.from(metaMap2);

    String sentence3 = "Heavy rain caused flooding in coastal towns yesterday.";
    Map<String, Object> metaMap3 = new HashMap<>();
    metaMap3.put("category", "weather");
    metaMap3.put("source", "alert");
    metaMap3.put("relevance", 5);
    Metadata meta3 = Metadata.from(metaMap3);

    try {
      // Step 3: Generate embeddings
      Embedding embedding1 = model.embed(sentence1).content();
      Embedding embedding2 = model.embed(sentence2).content();
      Embedding embedding3 = model.embed(sentence3).content();

      // Step 4: Initialize in-memory embedding store
      EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

      // Step 5: Add embeddings to the store with metadata
      embeddingStore.add(embedding1, TextSegment.from(sentence1, meta1));
      embeddingStore.add(embedding2, TextSegment.from(sentence2, meta2));
      embeddingStore.add(embedding3, TextSegment.from(sentence3, meta3));

      System.out.println("Embeddings with metadata added to the store successfully.");

      // Step 6: Prepare a query embedding
      Embedding queryEmbedding = model.embed("Which tech companies had profits?").content();

      Filter filter =
          new And(
              new And(
                  new IsEqualTo("source", "news"),
                  new IsIn("category", List.of("technology", "finance"))),
              new IsGreaterThanOrEqualTo("relevance", Integer.valueOf(9)));

      // Step 8: Build search request with filter
      EmbeddingSearchRequest request =
          EmbeddingSearchRequest.builder()
              .queryEmbedding(queryEmbedding)
              .maxResults(2)
              .minScore(0.5)
              .filter(filter)
              .build();

      // Step 9: Perform search
      EmbeddingSearchResult<TextSegment> result = embeddingStore.search(request);

      // Step 10: Display results
      System.out.println("\nFiltered & Ranked Results:");
      result
          .matches()
          .forEach(
              match -> {
                TextSegment segment = match.embedded();
                System.out.printf(
                    "Score: %.3f | Text: %s | Metadata: %s%n",
                    match.score(), segment.text(), segment.metadata());
              });

    } catch (Exception e) {
      System.err.println("An error occurred: " + e.getMessage());
      e.printStackTrace();
    }
  }
}
