package com.sample.app.util;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.ClassPathDocumentLoader;
import dev.langchain4j.data.document.parser.markdown.MarkdownDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import java.util.List;

public final class EmbeddingStoreUtil {

  private static final String KNOWLEDGE_FILE = "company-policies.md";

  private static final int MAX_SEGMENT_SIZE = 500;

  private static final int MAX_OVERLAP_SIZE = 50;

  private static final int MAX_RESULTS = 3;

  private static final double MIN_SCORE = 0.60;

  /*
   * Runs locally inside the Java process.
   *
   * No Ollama / external embedding service required.
   */
  private static final EmbeddingModel EMBEDDING_MODEL = new BgeSmallEnV15QuantizedEmbeddingModel();

  /*
   * In-memory vector store.
   */
  private static final EmbeddingStore<TextSegment> EMBEDDING_STORE = new InMemoryEmbeddingStore<>();

  static {
    initializeEmbeddingStore();
  }

  private EmbeddingStoreUtil() {}

  private static void initializeEmbeddingStore() {

    System.out.println();
    System.out.println("==========================================");
    System.out.println("       Initializing Embedding Store");
    System.out.println("==========================================");
    System.out.println();

    /*
     * STEP 1
     *
     * Load company-policies.md from src/main/resources.
     */
    Document document =
        ClassPathDocumentLoader.loadDocument(KNOWLEDGE_FILE, new MarkdownDocumentParser());

    System.out.println("Loaded document : " + KNOWLEDGE_FILE);

    /*
     * STEP 2
     *
     * Split the document into smaller segments.
     */
    List<TextSegment> segments =
        DocumentSplitters.recursive(MAX_SEGMENT_SIZE, MAX_OVERLAP_SIZE).split(document);

    System.out.println("Created segments: " + segments.size());

    /*
     * STEP 3
     *
     * Generate embeddings for all segments.
     */
    List<Embedding> embeddings = EMBEDDING_MODEL.embedAll(segments).content();

    System.out.println("Generated embeddings: " + embeddings.size());

    /*
     * STEP 4
     *
     * Store embedding + original segment.
     */
    EMBEDDING_STORE.addAll(embeddings, segments);

    System.out.println("Embeddings added to InMemoryEmbeddingStore");

    System.out.println();
    System.out.println("Embedding Store initialized successfully.");
    System.out.println();
  }

  public static List<TextSegment> findMatchingSegments(String userQuery) {

    System.out.println();
    System.out.println("------------------------------------------");
    System.out.println("        Semantic Similarity Search");
    System.out.println("------------------------------------------");
    System.out.println();

    System.out.println("User Query : " + userQuery);

    /*
     * BGE recommends adding a search instruction to query embeddings.
     */
    String embeddingQuery = "Represent this sentence for searching relevant passages: " + userQuery;

    /*
     * STEP 1
     *
     * Convert user query into embedding.
     */
    Embedding queryEmbedding = EMBEDDING_MODEL.embed(embeddingQuery).content();

    System.out.println("Query embedding generated.");

    /*
     * STEP 2
     *
     * Search for semantically similar segments.
     */
    EmbeddingSearchRequest request =
        EmbeddingSearchRequest.builder()
            .queryEmbedding(queryEmbedding)
            .maxResults(MAX_RESULTS)
            .minScore(MIN_SCORE)
            .build();

    EmbeddingSearchResult<TextSegment> searchResult = EMBEDDING_STORE.search(request);

    /*
     * STEP 3
     *
     * Display matches for demo purposes.
     */
    System.out.println();
    System.out.println("Matches found: " + searchResult.matches().size());

    System.out.println();

    searchResult
        .matches()
        .forEach(
            match -> {
              System.out.printf("Score   : %.4f%n", match.score());

              System.out.println("Content :");

              System.out.println(match.embedded().text());

              System.out.println("------------------------------------------");
            });

    /*
     * STEP 4
     *
     * Return matching TextSegments.
     */
    return searchResult.matches().stream().map(match -> match.embedded()).toList();
  }
}
