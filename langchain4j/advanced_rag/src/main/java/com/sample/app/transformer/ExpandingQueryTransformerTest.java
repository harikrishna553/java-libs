package com.sample.app.transformer;

import java.util.Collection;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.query.Metadata;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer;

public class ExpandingQueryTransformerTest {

    public static final int EXPECTED_QUERIES = 5;
    public static final int ITERATIONS = 100; // Run multiple times

    public static void main(String[] args) {
        // Step 1: Initialize the chat model (Ollama running locally)
        OllamaChatModel chatModel = OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.2")
                .build();

        // Step 2: Initialize chat memory with a windowed message history (up to 5 messages)
        ChatMemory chatMemory = MessageWindowChatMemory.builder()
                .id("12345")
                .maxMessages(5)
                .build();

        // Add previous user and AI messages to simulate conversation context
        chatMemory.add(UserMessage.from("I'm researching electric vehicles."));
        chatMemory.add(AiMessage.from("Sure! What aspect of EVs are you interested in?"));

        // Step 3: Create the verbose user query
        String userQuery = "Can you help me find battery performance comparisons for EVs released after 2022?";

        // Attach metadata including user message and chat history
        Metadata metadata = Metadata.from(UserMessage.from(userQuery), 1, chatMemory.messages());
        Query originalQuery = Query.from(userQuery, metadata);

        // Step 4: Create ExpandingQueryTransformer with expected number of queries
        ExpandingQueryTransformer transformer = new ExpandingQueryTransformer(chatModel, EXPECTED_QUERIES);

        // Counters for summary
        int totalValid = 0;
        int totalFailed = 0;

        // Step 5: Run multiple iterations and count results
        for (int i = 1; i <= ITERATIONS; i++) {
            Collection<Query> expandedQueries = transformer.transform(originalQuery);

            int count = expandedQueries.size();
            if (count == EXPECTED_QUERIES) {
                totalValid++;
            } else {
                totalFailed++;
            }
        }

        // Step 6: Print summary stats
        System.out.println("==== Summary Stats ====");
        System.out.println("Total runs: " + ITERATIONS);
        System.out.println("Total valid results: " + totalValid);
        System.out.println("Total failed results: " + totalFailed);
        double successRate = (totalValid * 100.0) / ITERATIONS;
        System.out.println("Success percentage: " + String.format("%.2f", successRate) + "%");
    }
}
