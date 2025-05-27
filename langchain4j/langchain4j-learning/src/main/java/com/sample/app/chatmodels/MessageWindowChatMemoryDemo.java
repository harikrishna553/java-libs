package com.sample.app.chatmodels;

import java.util.List;
import java.util.Scanner;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;

public class MessageWindowChatMemoryDemo {
	public static void main(String[] args) {
		// Build the Ollama language model
		ChatModel chatModel = OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

		// Create chat memory with token-based eviction policy
		ChatMemory memory = MessageWindowChatMemory.builder().id("12345").maxMessages(5).build();

		Scanner scanner = new Scanner(System.in);
		System.out.println("Chat with AI. Type 'exit' to quit.");

		while (true) {
			System.out.print("\nYou: ");
			String input = scanner.nextLine();

			if ("exit".equalsIgnoreCase(input.trim())) {
				System.out.println("Conversation ended.");
				break;
			}

			UserMessage userMessage = UserMessage.from(input);
			memory.add(userMessage);

			AiMessage aiMessage = chatModel.chat(memory.messages()).aiMessage();
			memory.add(aiMessage);

			System.out.println("AI: " + aiMessage.text());

			System.out.println("-------------------------------------");
			List<ChatMessage> chatMessages = memory.messages();
			for (ChatMessage chatMessage : chatMessages) {
				System.out.println(extractText(chatMessage));
			}
			System.out.println("-------------------------------------");

		}

		scanner.close();
	}

	private static String extractText(ChatMessage message) {
		if (message instanceof UserMessage) {
			UserMessage userMessage = (UserMessage) message;
			List<Content> contents = userMessage.contents();
			StringBuilder builder = new StringBuilder();
			for (Content content : contents) {
				TextContent textContent = (TextContent) content;
				builder.append(textContent.text());
			}
			return builder.toString();
		} else if (message instanceof AiMessage) {
			return ((AiMessage) message).text();
		} else if (message instanceof SystemMessage) {
			return ((SystemMessage) message).text();
		} else if (message instanceof ToolExecutionResultMessage) {
			return ((ToolExecutionResultMessage) message).text();
		} else {
			return ""; // fallback for unknown or null message
		}
	}
}
