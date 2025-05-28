package com.sample.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dto.ChatResponse;
import com.sample.app.interfaces.ChatAssistant;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

@Service
public class ChatService {
	@Autowired
	private OllamaChatModel chatModel;

	private ChatAssistant chatAssistant;

	public ChatResponse chat(Integer memoryId, String userMessage) {
		if (chatAssistant == null) {
			synchronized (ChatService.class) {
				if (chatAssistant == null) {
					chatAssistant = AiServices.builder(ChatAssistant.class).chatModel(chatModel)
							.chatMemoryProvider(chatMemoryId -> MessageWindowChatMemory.withMaxMessages(10)).build();
				}

			}

		}

		String story = chatAssistant.chat(memoryId, userMessage);
		return new ChatResponse(story);
	}
}
