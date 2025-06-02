package com.sample.app.llm;

public class ConversationResponse {
	private String message;

	public ConversationResponse() {
	}

	public ConversationResponse(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
