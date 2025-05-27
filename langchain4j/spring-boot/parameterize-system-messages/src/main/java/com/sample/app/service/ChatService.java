package com.sample.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dto.ChatResponse;
import com.sample.app.interfaces.StoryTeller;

import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;

@Service
public class ChatService {
	@Autowired
	private OllamaChatModel chatModel;

	public ChatResponse chat(String userMessage, String authorLike) {
		StoryTeller storyTeller = AiServices.create(StoryTeller.class, chatModel);
		String story = storyTeller.chat(userMessage, authorLike);
		return new ChatResponse(story);
	}
}
