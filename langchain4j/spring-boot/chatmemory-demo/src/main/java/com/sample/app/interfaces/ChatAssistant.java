package com.sample.app.interfaces;

import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.memory.ChatMemoryAccess;

public interface ChatAssistant extends ChatMemoryAccess{
	String chat(@MemoryId int memoryId, @UserMessage String message);
}
