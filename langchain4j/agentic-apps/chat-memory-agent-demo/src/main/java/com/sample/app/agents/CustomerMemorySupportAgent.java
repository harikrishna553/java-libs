package com.sample.app.agents;

import com.sample.app.config.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatMemoryProviderSupplier;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.MemoryId;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface CustomerMemorySupportAgent {

    @SystemMessage("""
            You are a helpful customer support assistant.

            Remember information belonging to the current customer
            and use it when answering follow-up questions.
            Never mix information between customers.
            """)

    @UserMessage("""
            {{message}}
            """)

    @Agent(
            name = "CustomerMemorySupportAgent",
            outputKey = "response",
            description = "Customer support agent with isolated memory per customer"
    )
    String chat(
            @MemoryId String customerId,
            @V("message") String message
    );


    @ChatMemoryProviderSupplier
    static ChatMemory chatMemory(Object memoryId) {

        System.out.println(
                "CustomerMemorySupportAgent -> Creating memory for: "
                        + memoryId
        );

        return MessageWindowChatMemory.withMaxMessages(10);
    }


    @ChatModelSupplier
    static ChatModel chatModel() {

        return OllamaModels.fastModel();
    }
}