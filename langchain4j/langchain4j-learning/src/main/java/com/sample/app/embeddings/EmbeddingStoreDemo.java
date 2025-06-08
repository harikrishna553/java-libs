package com.sample.app.embeddings;

import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class EmbeddingStoreDemo {

	public static void main(String[] args) {

		// Step 1: Initialize embedding model
		EmbeddingModel model = new BgeSmallEnV15QuantizedEmbeddingModel();

		// Step 2: Prepare text data
		String sentence1 = "The stock market surged as tech companies reported strong earnings.";
		String sentence2 = "Tech giants like Apple and Amazon saw record profits this quarter.";
		String sentence3 = "Heavy rain caused flooding in coastal towns yesterday.";

		try {
			// Step 3: Generate embeddings
			Embedding embedding1 = model.embed(sentence1).content();
			Embedding embedding2 = model.embed(sentence2).content();
			Embedding embedding3 = model.embed(sentence3).content();

			// Step 4: Initialize in-memory embedding store
			EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();

			// Step 5: Add embeddings to the store
			embeddingStore.add(embedding1, TextSegment.from(sentence1));
			embeddingStore.add(embedding2, TextSegment.from(sentence2));
			embeddingStore.add(embedding3, TextSegment.from(sentence3));

			System.out.println("Embeddings added to the store successfully.");

			// Step 6: Perform a search
			Embedding queryEmbedding = model.embed("Which companies had strong earnings?").content();
			EmbeddingSearchRequest request = EmbeddingSearchRequest.builder().queryEmbedding(queryEmbedding)
					.maxResults(2).minScore(0.5).build();

			EmbeddingSearchResult<TextSegment> embeddingSearchResult = embeddingStore.search(request);

			// Step 7: Display results
			System.out.println("\nTop relevant results:");
			embeddingSearchResult.matches().forEach(match -> System.out.printf("Score: %.3f | Text: %s%n",
					match.score(), match.embedded() != null ? match.embedded().text() : "[No TextSegment stored]"));

		} catch (Exception e) {
			System.err.println("An error occurred while embedding or storing data: " + e.getMessage());
			e.printStackTrace();
		}
	}
}
