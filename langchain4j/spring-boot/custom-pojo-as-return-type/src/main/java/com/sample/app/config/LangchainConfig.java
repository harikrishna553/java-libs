package com.sample.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import dev.langchain4j.http.client.spring.restclient.SpringRestClientBuilderFactory;
import dev.langchain4j.model.chat.request.ResponseFormat;
import dev.langchain4j.model.ollama.OllamaChatModel;

@Configuration
public class LangchainConfig {

	@Bean
	public OllamaChatModel ollamaLanguageModel() {
		return OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2")
				.responseFormat(ResponseFormat.JSON).temperature(0.2)
				.logRequests(true)
				.httpClientBuilder(new SpringRestClientBuilderFactory().create()) // explicitly use Spring's HTTP client
				.build();
	}
}
