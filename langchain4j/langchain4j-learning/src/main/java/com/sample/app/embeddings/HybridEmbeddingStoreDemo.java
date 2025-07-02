package com.sample.app.embeddings;

import java.nio.file.Path;
import java.util.List;
import java.util.Map;

import dev.langchain4j.data.document.Metadata;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.store.embedding.EmbeddingSearchRequest;
import dev.langchain4j.store.embedding.EmbeddingSearchResult;
import dev.langchain4j.store.embedding.EmbeddingStore;

public class HybridEmbeddingStoreDemo {

	public static void main(String[] args) {
		// Step 1: Initialize embedding model
		EmbeddingModel model = new BgeSmallEnV15QuantizedEmbeddingModel();

		// Step 2: Define meaningful and diverse sentences
		List<String> sentences = List.of("Apple released a new iPhone with advanced AI capabilities.",
				"Tesla's new model is fully autonomous and electric.",
				"The World Health Organization issued a warning about a new virus.",
				"Machine learning is transforming the financial industry.",
				"Google's search algorithm uses advanced natural language processing.",
				"The president announced new economic policies to combat inflation.",
				"A new study shows the benefits of a Mediterranean diet.",
				"SpaceX launched another rocket to the International Space Station.",
				"Football fans are excited about the upcoming World Cup.", "Scientists discovered water on Mars.",
				"The local government introduced new recycling initiatives.",
				"Amazon is investing in drone delivery technology.",
				"ChatGPT is a popular AI chatbot developed by OpenAI.",
				"Virtual Reality is changing how we experience games.",
				"Bitcoin prices fell sharply amid market uncertainty.",
				"Climate change poses significant risks to coastal cities.",
				"The new vaccine was found to be 95% effective.",
				"Harvard researchers developed a new method for clean energy.",
				"The stock market responded positively to the tech earnings report.",
				"Students protested against tuition hikes in several universities.",
				"NASA is preparing for a crewed mission to the Moon.",
				"Twitter is undergoing a major redesign to improve user engagement.",
				"Heavy snowfall disrupted transport in the northeast region.",
				"The Olympics will feature new sports like skateboarding and surfing.",
				"The Supreme Court ruled on a significant privacy case.",
				"AI is being used to detect fraudulent transactions in banks.",
				"Facebook is rebranding to focus more on the metaverse.",
				"Self-driving cars rely heavily on real-time sensor data.",
				"A famous singer released a new album topping global charts.",
				"Researchers are exploring the use of quantum computing in medicine.",
				"YouTube is testing a new recommendation algorithm.",
				"The film festival attracted entries from over 40 countries.",
				"Drought conditions are affecting crops in the Midwest.",
				"Robotics is helping automate warehouse operations.",
				"The CEO of Microsoft shared insights on cloud computing growth.",
				"A new law aims to regulate cryptocurrency exchanges.",
				"India's Chandrayaan mission reached the lunar south pole.",
				"Mental health apps are becoming increasingly popular.",
				"Google Maps added features for visually impaired users.",
				"The job market is recovering post-pandemic.", "An earthquake struck the western part of the country.",
				"Cybersecurity threats are on the rise globally.",
				"5G networks promise faster internet and lower latency.",
				"Researchers discovered a new species in the Amazon rainforest.",
				"Education systems are shifting toward hybrid learning models.",
				"The e-commerce boom is straining global supply chains.",
				"A startup is building plant-based alternatives to meat.",
				"Streaming services are reshaping the entertainment industry.",
				"The city implemented smart traffic management systems.",
				"Libraries are evolving into community learning centers.",
				"Electric bikes are gaining popularity in urban areas.");

		// Step 3: Create and store embeddings
		try {
			EmbeddingStore<TextSegment> embeddingStore = new HybridEmbeddingStore<>(
					Path.of("/Users/Shared/chunks_dir"));

			for (int i = 0; i < sentences.size(); i++) {
				String sentence = sentences.get(i);
				Embedding embedding = model.embed(sentence).content();
				Metadata metadata = Metadata
						.from(Map.of("index", String.valueOf(i + 1), "category", categorize(sentence)));
				embeddingStore.add(embedding, TextSegment.from(sentence, metadata));
			}

			System.out.println("50+ embeddings added successfully.");

			// Step 4: Search with a meaningful question
			String query = "Which companies are using artificial intelligence?";
			Embedding queryEmbedding = model.embed(query).content();
			EmbeddingSearchRequest request = EmbeddingSearchRequest.builder().queryEmbedding(queryEmbedding)
					.maxResults(5).minScore(0.5).build();

			EmbeddingSearchResult<TextSegment> result = embeddingStore.search(request);

			// Step 5: Display results
			System.out.println("\nTop Matches for Query: \"" + query + "\"");
			result.matches().forEach(match -> System.out.printf("Score: %.3f | Text: %s | Metadata: %s%n",
					match.score(), match.embedded().text(), match.embedded().metadata()));

		} catch (Exception e) {
			System.err.println("Error occurred: " + e.getMessage());
			e.printStackTrace();
		}
	}

	private static String categorize(String sentence) {
		sentence = sentence.toLowerCase();
		if (sentence.contains("ai") || sentence.contains("machine learning") || sentence.contains("chatgpt")) {
			return "Artificial Intelligence";
		} else if (sentence.contains("space") || sentence.contains("nasa") || sentence.contains("mars")) {
			return "Space";
		} else if (sentence.contains("stock") || sentence.contains("market") || sentence.contains("economy")) {
			return "Finance";
		} else if (sentence.contains("health") || sentence.contains("vaccine") || sentence.contains("virus")) {
			return "Health";
		} else if (sentence.contains("sports") || sentence.contains("football") || sentence.contains("olympics")) {
			return "Sports";
		} else if (sentence.contains("climate") || sentence.contains("rainforest") || sentence.contains("drought")) {
			return "Environment";
		} else {
			return "General";
		}
	}
}
