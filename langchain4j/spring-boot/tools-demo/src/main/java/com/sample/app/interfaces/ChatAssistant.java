package com.sample.app.interfaces;

import dev.langchain4j.service.UserMessage;

public interface ChatAssistant {
	String chat(@UserMessage String message);
}
