package com.sample.app.chatmodels;

import java.util.Collections;

import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.request.ChatRequest;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ChatModelHelloWorld {

	public static void main(String[] args) {
		// Create the Ollama language model
		ChatModel chatModel = OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

		// Build a ChatRequest with custom parameters
		ChatRequest chatRequest = ChatRequest.builder()
				.messages(
						Collections.singletonList(UserMessage.from("What are the benefits of using AI in education?")))
				.temperature(0.7).maxOutputTokens(200).build();

		// Send the request and receive the response
		ChatResponse chatResponse = chatModel.chat(chatRequest);

		// Print the AI response
		System.out.println("AI Response: " + chatResponse.aiMessage());
	}
}
