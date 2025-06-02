package com.sample.app.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.sample.app.dto.ChatResponse;
import com.sample.app.interfaces.ChatAssistant;
import com.sample.app.tools.MathTools;

import dev.langchain4j.agent.tool.ToolExecutionRequest;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.tool.ToolExecution;

@Service
public class ChatService {
	@Autowired
	private OllamaChatModel chatModel;

	private ChatAssistant chatAssistant;

	public ChatResponse chat(String userMessage) throws StreamWriteException, DatabindException, IOException {
		if (chatAssistant == null) {
			synchronized (ChatService.class) {
				if (chatAssistant == null) {
					chatAssistant = AiServices.builder(ChatAssistant.class).chatModel(chatModel).tools(new MathTools())
							.build();
				}
			}
		}

		Result<String> chatResponse = chatAssistant.chat(userMessage);

		List<String> toolsUsed = new ArrayList<>();

		List<ToolExecution> toolsExecuted = chatResponse.toolExecutions();
		for (ToolExecution toolExecution : toolsExecuted) {
			ToolExecutionRequest toolExecutionRequest = toolExecution.request();

			String arguments = toolExecutionRequest.arguments();
			String toolName = toolExecutionRequest.name();

			toolsUsed.add(toolName + "(" + arguments + ")");

		}

		return new ChatResponse(chatResponse.content(), toolsUsed);
	}
}
