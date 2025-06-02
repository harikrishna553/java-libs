package com.sample.app.dto;

import java.util.List;

public class ChatResponse {
	private String message;
	private List<String> tools;

	public ChatResponse() {
	}

	public ChatResponse(String message, List<String> tools) {
		this.message = message;
		this.tools = tools;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public List<String> getTools() {
		return tools;
	}

	public void setTools(List<String> tools) {
		this.tools = tools;
	}

}
