package com.sample.app.chatmodels;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class ChatConversation {

	public static void main(String[] args) {
		// Create the Ollama language model
		ChatModel chatModel = OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

		UserMessage firstUserMessage = UserMessage.from("Hello, my name is Krishna");
		AiMessage firstAiMessage = chatModel.chat(firstUserMessage).aiMessage();
		System.out.println(firstAiMessage.text());

		UserMessage secondUserMessage = UserMessage.from("What is my name?");
		AiMessage secondAiMessage = chatModel.chat(
		    firstUserMessage, 
		    firstAiMessage, 
		    secondUserMessage
		).aiMessage(); // Krishna

		// Print the AI response
		System.out.println("AI Response: " + secondAiMessage.text());
	}
}
