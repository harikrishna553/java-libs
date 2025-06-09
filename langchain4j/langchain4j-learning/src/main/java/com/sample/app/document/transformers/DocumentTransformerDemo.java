package com.sample.app.document.transformers;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentTransformer;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DocumentTransformerDemo {

  public static void main(String[] args) {

    Document doc =
        Document.from(
            """
				This is just a simple     example document.
				It is meant to demonstrate a few really basic transformations.
				There are some     unnecessary spaces and very redundant words.
				""");

    // Print the content before transformation
    System.out.println("Before Transformation:\n" + doc.text());

    // Transformer 1: Trim whitespace in each line
    DocumentTransformer trimWhitespaceTransformer =
        document -> {
          String transformed =
              Arrays.stream(document.text().split("\n"))
                  .map(String::trim)
                  .collect(Collectors.joining("\n"));
          return Document.from(transformed);
        };

    // Transformer 2: Convert to lowercase
    DocumentTransformer lowercaseTransformer =
        document -> {
          String transformed = document.text().toLowerCase();
          return Document.from(transformed);
        };

    // Transformer 3: Remove filler words
    DocumentTransformer removeFillerWordsTransformer =
        document -> {
          List<String> fillerWords =
              List.of("just", "really", "very", "actually", "basically", "simply");
          String transformed =
              Arrays.stream(document.text().split("\\s+"))
                  .filter(word -> !fillerWords.contains(word.toLowerCase()))
                  .collect(Collectors.joining(" "));
          return Document.from(transformed);
        };

    // Apply all transformations sequentially
    Document transformedDoc = doc;
    transformedDoc = trimWhitespaceTransformer.transform(transformedDoc);
    transformedDoc = lowercaseTransformer.transform(transformedDoc);
    transformedDoc = removeFillerWordsTransformer.transform(transformedDoc);

    // Print the content after transformation
    System.out.println("\nAfter Transformation:\n" + transformedDoc.text());
  }
}
