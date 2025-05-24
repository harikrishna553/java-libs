package com.sample.app.ollama;

import dev.langchain4j.model.ollama.OllamaLanguageModel;
import dev.langchain4j.model.output.Response;

public class HelloWorld {
	public static void main(String[] args) {
		OllamaLanguageModel model = OllamaLanguageModel.builder().baseUrl("http://localhost:11434") 
				.modelName("llama3.2")
				.build();

		String prompt = "Tell me some Interesting Fact About LLMs in maximum 30 words";
		Response<String> response = model.generate(prompt);

		System.out.println("Response: " + response.content());
	}
}
