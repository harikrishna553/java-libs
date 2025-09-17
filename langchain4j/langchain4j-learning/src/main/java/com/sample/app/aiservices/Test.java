package com.sample.app.aiservices;

import java.util.*;

public class Test {
	
	public static void main(String[] args) {
		 enum Sentiment {
		     POSITIVE, NEUTRAL, NEGATIVE
		 }

		  Map<Sentiment, List<String>> examples = Map.of(
				  Sentiment.POSITIVE, List.of("This is great!", "Wow, awesome!"),
				  Sentiment.NEUTRAL,  List.of("Well, it's fine", "It's ok"),
				  Sentiment.NEGATIVE, List.of("It is pretty bad", "Worst experience ever!")
		 );

		 EmbeddingModel embeddingModel = new AllMiniLmL6V2QuantizedEmbeddingModel();

		 TextClassifier<Sentiment> classifier = new EmbeddingModelTextClassifier<>(embeddingModel, examples);

		 List<Sentiment> sentiments = classifier.classify("Awesome!");
		 System.out.println(sentiments); // [POSITIVE]
	}

}
