package com.sample.app.chatmodels;

import java.time.Duration;

import dev.langchain4j.model.chat.Capability;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class Models {

	private static final String BASE_URL = "http://localhost:11434";

	private static final String BASIC_MODEL = "llama3.2";

	private Models() {
	}

	public static ChatModel baseModel() {

		return OllamaChatModel.builder().baseUrl(BASE_URL).modelName(BASIC_MODEL).temperature(0.0)
				.timeout(Duration.ofSeconds(120)).logRequests(false).logResponses(false)
				.supportedCapabilities(Capability.RESPONSE_FORMAT_JSON_SCHEMA).build();
	}

}
