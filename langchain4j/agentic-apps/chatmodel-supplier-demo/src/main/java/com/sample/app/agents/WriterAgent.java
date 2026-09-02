package com.sample.app.agents;

import com.sample.app.config.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;

public interface WriterAgent {

    @UserMessage("""
            You are a technical content writer.

            Topic:

            {{topic}}

            Research:

            {{research}}

            Write a clear technical explanation
            based on the research.

            The requested quality level is:

            {{qualityLevel}}

            Return only the final content.
            """)
    @Agent(
            name = "WriterAgent",
            outputKey = "article",
            description = "Creates the final article from research"
    )
    String write(
            @V("topic") String topic,
            @V("research") String research,
            @V("qualityLevel") QualityLevel qualityLevel
    );


    @ChatModelSupplier
    static ChatModel chatModel(
            @V("qualityLevel") QualityLevel qualityLevel) {

        System.out.println();

        if (qualityLevel == QualityLevel.HIGH) {

            System.out.println(
                    "WriterAgent -> Dynamic model -> "
                            + OllamaModels.QUALITY_MODEL_NAME
            );

            return OllamaModels.qualityModel();
        }

        System.out.println(
                "WriterAgent -> Dynamic model -> "
                        + OllamaModels.FAST_MODEL_NAME
        );

        return OllamaModels.fastModel();
    }
}