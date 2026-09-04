package com.sample.app.agents;

import com.sample.app.config.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatMemorySupplier;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface SharedMemorySupportAgent {

    @SystemMessage("""
            You are a helpful customer support assistant.

            Remember information provided earlier in the conversation
            and use it when answering follow-up questions.
            """)

    @UserMessage("""
            {{message}}
            """)

    @Agent(
            name = "SharedMemorySupportAgent",
            outputKey = "response",
            description = "Customer support agent using shared chat memory"
    )
    String chat(@V("message") String message);


    @ChatMemorySupplier
    static ChatMemory chatMemory() {

        System.out.println(
                "SharedMemorySupportAgent -> Creating shared ChatMemory"
        );

        return MessageWindowChatMemory.withMaxMessages(10);
    }


    @ChatModelSupplier
    static ChatModel chatModel() {

        return OllamaModels.fastModel();
    }
}