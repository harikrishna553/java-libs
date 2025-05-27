package com.sample.app.chatmodels;

import java.util.List;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.data.message.Content;
import dev.langchain4j.data.message.SystemMessage;
import dev.langchain4j.data.message.TextContent;
import dev.langchain4j.data.message.ToolExecutionResultMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.TokenCountEstimator;

public class SimpleTokenCountEstimator implements TokenCountEstimator {

	private static final double TOKENS_PER_WORD = 1.33;

	@Override
	public int estimateTokenCountInText(String text) {
		if (text == null || text.isBlank()) {
			return 0;
		}
		int wordCount = text.trim().split("\\s+").length;
		return (int) Math.ceil(wordCount * TOKENS_PER_WORD);
	}

	@Override
	public int estimateTokenCountInMessage(ChatMessage message) {
		String text = extractText(message);
		return estimateTokenCountInText(text);
	}

	@Override
	public int estimateTokenCountInMessages(Iterable<ChatMessage> messages) {
		int total = 0;
		for (ChatMessage message : messages) {
			total += estimateTokenCountInMessage(message);
		}
		return total;
	}

	private String extractText(ChatMessage message) {
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
