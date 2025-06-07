package com.sample.app.embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.*;

public class LocalEmbeddingExample {

    public static void main(String[] args) {
        // Step 1: Create the quantized embedding model
        EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();

        // Step 2: Define your input text
        String text = "LangChain4j simplifies working with LLMs in Java.";

        // Step 3: Generate the embedding
        Embedding embedding = embeddingModel.embed(text).content();

        // Step 4: Print the results
        System.out.println("Embedding vector:");
        System.out.println(embedding.vectorAsList());
        System.out.println("Embedding dimension: " + embedding.dimension());
    }
}
