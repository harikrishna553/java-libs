package com.sample.app.agents;

import com.sample.app.tools.CustomerSupportToolProvider;
import com.sample.app.utils.OllamaModels;

import dev.langchain4j.agentic.Agent;
import dev.langchain4j.agentic.declarative.ChatModelSupplier;
import dev.langchain4j.agentic.declarative.ToolProviderSupplier;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.SystemMessage;
import dev.langchain4j.service.UserMessage;
import dev.langchain4j.service.V;
import dev.langchain4j.service.tool.ToolProvider;

public interface DynamicToolsCustomerSupportAgent {

    @SystemMessage("""
            You are a customer support agent.

            You help customers with:
            - order status
            - shipping information
            - refund eligibility

            IMPORTANT RULES:

            1. Use the tools made available to you
               whenever customer-specific information
               is required.

            2. Never invent order information.

            3. Base the final answer on tool results.

            4. Keep the response concise and helpful.
            """)
    @UserMessage("""
            Customer request:

            {{request}}
            """)
    @Agent(
            "Handles customer support using dynamically provided tools"
    )
    String support(
            @V("request") String request
    );


    @ToolProviderSupplier
    static ToolProvider toolProvider() {

        System.out.println();
        System.out.println(
                "========================================"
        );

        System.out.println(
                "@ToolProviderSupplier invoked"
        );

        System.out.println(
                "Creating CustomerSupportToolProvider"
        );

        System.out.println(
                "========================================"
        );

        return new CustomerSupportToolProvider();
    }


    @ChatModelSupplier
    static ChatModel chatModel() {

        return OllamaModels.chatModel();
    }
    
    default boolean isDynamic() {
        return true;
    }
}