package com.sample.app.llm;

public class ChatMessageRequest {

	private String message;
	private ChatMessageType messageType;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ChatMessageType getMessageType() {
		return messageType;
	}

	public void setMessageType(ChatMessageType messageType) {
		this.messageType = messageType;
	}

}
