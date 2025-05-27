package com.sample.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sample.app.dto.ChatResponse;

import dev.langchain4j.model.language.LanguageModel;

@Service
public class ChatService {

	@Autowired
    private LanguageModel model;

    public ChatResponse chat(String userMessage) {
        String responseMessage = model.generate(userMessage).content();
        return new ChatResponse(responseMessage);
    }
}
