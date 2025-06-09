package com.sample.app.embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.CosineSimilarity;

public class CosineSimilarityExample {

  public static void main(String[] args) {
    // Initialize local embedding model
    EmbeddingModel model = new BgeSmallEnV15QuantizedEmbeddingModel();

    // Define sentences
    String sentence1 = "The stock market surged as tech companies reported strong earnings.";
    String sentence2 = "Tech giants like Apple and Amazon saw record profits this quarter.";
    String sentence3 = "Heavy rain caused flooding in coastal towns yesterday.";

    // Generate embeddings
    Embedding embedding1 = model.embed(sentence1).content();
    Embedding embedding2 = model.embed(sentence2).content();
    Embedding embedding3 = model.embed(sentence3).content();

    // Compute cosine similarities
    double sim1vs2 = CosineSimilarity.between(embedding1, embedding2);
    double sim1vs3 = CosineSimilarity.between(embedding1, embedding3);
    double sim2vs3 = CosineSimilarity.between(embedding2, embedding3);

    // Display results
    System.out.printf("Similarity between sentence 1 and 2: %.4f%n", sim1vs2);
    System.out.printf("Similarity between sentence 1 and 3: %.4f%n", sim1vs3);
    System.out.printf("Similarity between sentence 2 and 3: %.4f%n", sim2vs3);
  }
}
