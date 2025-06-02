package com.sample.app.llm;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.response.ChatResponse;
import dev.langchain4j.model.ollama.OllamaChatModel;

@Service
public class ChatService {
	@Autowired
	private OllamaChatModel chatModel;

	public ConversationResponse chat(List<ChatMessageRequest> chatMessageRequests) {
		ChatResponse response = chatModel.chat(from(chatMessageRequests));
		return new ConversationResponse(response.aiMessage().text());
	}

	private static List<ChatMessage> from(List<ChatMessageRequest> chatMessageRequests) {
		List<ChatMessage> chatMessages = new ArrayList<>();
		chatMessages.add(new SystemMessage("You are a helpful Chat Assistant"));

		for (ChatMessageRequest chatMessageRequest : chatMessageRequests) {
			if (ChatMessageType.USER == chatMessageRequest.getMessageType()) {
				chatMessages.add(new UserMessage(chatMessageRequest.getMessage()));
			} else if (ChatMessageType.AI == chatMessageRequest.getMessageType()) {
				chatMessages.add(new AiMessage(chatMessageRequest.getMessage()));
			}
		}

		return chatMessages;
	}
}
