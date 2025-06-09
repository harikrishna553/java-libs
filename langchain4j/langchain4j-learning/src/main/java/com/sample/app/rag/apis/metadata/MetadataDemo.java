package com.sample.app.rag.apis.metadata;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MetadataDemo {
  public static void main(String[] args) {

    // Step 1: Create metadata using static method
    Metadata metadata =
        Metadata.from("title", "Langchain4j Java API Reference")
            .put("author", "Langchain Team")
            .put("version", 2)
            .put("source", "https://docs.langchain4j.dev")
            .put("id", UUID.randomUUID())
            .put("lastUpdated", "2025-06-01");

    // Step 2: Create a document with content and metadata
    String content = "This section covers the Metadata class and its usage in Langchain4j.";
    Document document = Document.from(content, metadata);

    // Step 3: Print document metadata
    System.out.println("Document Metadata:");
    document.metadata().toMap().forEach((k, v) -> System.out.println(k + " => " + v));

    // Step 4: Add additional metadata dynamically
    Map<String, Object> extraMeta = new HashMap<>();
    extraMeta.put("category", "Java SDK");

    document.metadata().putAll(extraMeta);

    // Print updated metadata
    System.out.println("\nUpdated Metadata:");
    document.metadata().toMap().forEach((k, v) -> System.out.println(k + " => " + v));
  }
}
