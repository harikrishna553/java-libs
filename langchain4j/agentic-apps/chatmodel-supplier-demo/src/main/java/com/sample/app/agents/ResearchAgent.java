package com.sample.app.agents;

import com.sample.app.config.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface ResearchAgent {

    @UserMessage("""
            You are a research assistant.

            Research the following topic using your existing knowledge:

            {{topic}}

            Provide:
            - 5 important points
            - key concepts
            - practical considerations

            Keep the response concise.
            """)
    @Agent(
            name = "ResearchAgent",
            outputKey = "research",
            description = "Researches the topic and produces key points"
    )
    String research(
            @V("topic") String topic
    );


    @ChatModelSupplier
    static ChatModel chatModel() {

        System.out.println();
        System.out.println(
                "ResearchAgent -> Fixed model -> "
                        + OllamaModels.QUALITY_MODEL_NAME
        );

        return OllamaModels.qualityModel();
    }
}