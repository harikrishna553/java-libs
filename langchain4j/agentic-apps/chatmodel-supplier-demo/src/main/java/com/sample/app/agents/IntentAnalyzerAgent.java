package com.sample.app.agents;

import com.sample.app.config.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface IntentAnalyzerAgent {

    @UserMessage("""
            Analyze the following user request:

            {{topic}}

            Determine the appropriate quality level.

            Choose:

            INSTANT
            - Simple questions
            - Short explanations
            - Quick answers
            - Minimal reasoning required

            MEDIUM
            - Moderate technical explanation
            - Some reasoning is required
            - Examples may be needed
            - Normal level of detail

            HIGH
            - Complex technical topics
            - Detailed analysis is required
            - Comparisons or architecture discussions
            - Deep reasoning
            - Comprehensive explanation required
            """)
    @Agent(
            name = "IntentAnalyzerAgent",
            outputKey = "qualityLevel",
            description = "Determines the quality level required for the user request"
    )
    QualityLevel analyzeIntent(
            @V("topic") String topic
    );


    @ChatModelSupplier
    static ChatModel chatModel() {

        System.out.println(
                "IntentAnalyzerAgent -> "
                        + OllamaModels.FAST_MODEL_NAME
        );

        return OllamaModels.fastStructuredModel();
    }
}