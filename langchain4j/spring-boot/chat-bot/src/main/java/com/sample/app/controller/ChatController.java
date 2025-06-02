package com.sample.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.llm.ChatRequestBody;
import com.sample.app.llm.ChatService;
import com.sample.app.llm.ConversationResponse;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
@Tag(name = "Chat Controller", description = "This section contains APIs related to Chat APIs Powered by Ollama")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping
	public ConversationResponse chat(@RequestBody @Valid ChatRequestBody chatRequestBody) {
		return chatService.chat(chatRequestBody.getChatMessageRequests());
	}

}
