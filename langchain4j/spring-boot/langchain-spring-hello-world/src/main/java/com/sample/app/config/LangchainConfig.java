package com.sample.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilderFactory;
import dev.langchain4j.model.ollama.OllamaLanguageModel;

@Configuration
public class LangchainConfig {

	@Bean
	public OllamaLanguageModel ollamaLanguageModel() {
		return OllamaLanguageModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2")
				.httpClientBuilder(new SpringRestClientBuilderFactory().create()) // explicitly use Spring's HTTP client
				.build();
	}
}
