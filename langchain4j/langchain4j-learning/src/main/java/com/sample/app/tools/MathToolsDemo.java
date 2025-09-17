package com.sample.app.tools;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.Result;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.tool.ToolService;

public class MathToolsDemo {
	
	public interface ChatAssistant {
		Result<String> chat(@UserMessage String message);
	}

	
	public static void main(String[] args) {
		
		//ToolService se;
		
		 ChatModel chatModel =
			        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();
		 
		ChatAssistant chatAssistant = AiServices.builder(ChatAssistant.class).chatModel(chatModel).tools(new MathTools())
				.build();
		
		Result<String> response = chatAssistant.chat("What is 300 radians in degrees");
		System.out.println(response.content());
	}

}
