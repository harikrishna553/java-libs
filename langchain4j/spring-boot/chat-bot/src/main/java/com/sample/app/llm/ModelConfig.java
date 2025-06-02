package com.sample.app.llm;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilderFactory;
import dev.langchain4j.model.ollama.OllamaChatModel;

@Configuration
public class ModelConfig {
	@Bean
	public OllamaChatModel ollamaLanguageModel() {
		return OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2")
				.httpClientBuilder(new SpringRestClientBuilderFactory().create())
				.build();
	}

}
