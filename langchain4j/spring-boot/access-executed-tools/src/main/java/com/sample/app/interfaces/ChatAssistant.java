package com.sample.app.interfaces;

import dev.langchain4j.service.Result;
import dev.langchain4j.service.UserMessage;

public interface ChatAssistant {
	Result<String> chat(@UserMessage String message);
}
