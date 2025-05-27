package com.sample.app.chatmodels;

import java.time.Duration;

import dev.langchain4j.model.ollama.OllamaLanguageModel;
import dev.langchain4j.model.output.Response;

public class ModelParamsDemo {
	public static void main(String[] args) {
		OllamaLanguageModel model = OllamaLanguageModel.builder().baseUrl("http://localhost:11434") 
				.modelName("llama3.2")
				.temperature(0.3)
				.maxRetries(2)
				.timeout(Duration.ofMinutes(1))
				.build();

		String prompt = "Tell me some Interesting Fact About LLMs in maximum 30 words";
		Response<String> response = model.generate(prompt);

		System.out.println("Response: " + response.content());
	}
}
