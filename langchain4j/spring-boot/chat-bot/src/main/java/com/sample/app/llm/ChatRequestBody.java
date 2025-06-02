package com.sample.app.llm;

import java.util.List;

public class ChatRequestBody {

	List<ChatMessageRequest> chatMessageRequests;

	public List<ChatMessageRequest> getChatMessageRequests() {
		return chatMessageRequests;
	}

	public void setChatMessageRequests(List<ChatMessageRequest> chatMessageRequests) {
		this.chatMessageRequests = chatMessageRequests;
	}

}
