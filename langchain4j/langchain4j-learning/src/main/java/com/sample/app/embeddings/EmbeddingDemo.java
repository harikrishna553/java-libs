package com.sample.app.embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.store.embedding.CosineSimilarity;

public class EmbeddingDemo {
  public static void main(String[] args) {
    CosineSimilarity.between(null, null);
    Embedding embedding = new Embedding(null);
    embedding.normalize();
  }
}
