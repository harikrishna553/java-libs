package com.sample.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dto.ChatResponse;
import com.sample.app.interfaces.ChatAssistant;
import com.sample.app.tools.MathTools;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

@Service
public class ChatService {
	@Autowired
	private OllamaChatModel chatModel;

	private ChatAssistant chatAssistant;

	public ChatResponse chat(String userMessage) {
		if (chatAssistant == null) {
			synchronized (ChatService.class) {
				if (chatAssistant == null) {
					chatAssistant = AiServices.builder(ChatAssistant.class).chatModel(chatModel).tools(new MathTools())
							.build();
				}
			}
		}

		String story = chatAssistant.chat(userMessage);
		return new ChatResponse(story);
	}
}
