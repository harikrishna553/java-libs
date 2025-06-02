package com.sample.app.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.sample.app.dto.ChatResponse;
import com.sample.app.service.ChatService;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;

@RestController
@RequestMapping("/api/chat")
@CrossOrigin("*")
@Tag(name = "Chat Controller", description = "This section contains APIs related to Chat APIs Powered by Ollama")
public class ChatController {

	@Autowired
	private ChatService chatService;

	@PostMapping
	public ChatResponse chat(@RequestBody @Valid ChatRequestBody chatRequestBody) throws StreamWriteException, DatabindException, IOException {
		return chatService.chat(chatRequestBody.getMessage());
	}

	private static class ChatRequestBody {
		@NotEmpty
		private String message;

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}



	}
}
