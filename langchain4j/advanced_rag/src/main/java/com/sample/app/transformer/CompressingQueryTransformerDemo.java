package com.sample.app.transformer;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.query.Metadata;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.transformer.CompressingQueryTransformer;
import java.util.Collection;

/**
 * Demonstrates the use of {@link CompressingQueryTransformer} in Langchain4j with an Ollama LLM to
 * compress verbose user queries based on prior conversation context.
 *
 * <p>This example sets up:
 *
 * <ul>
 *   <li>A basic Ollama LLM chat model using llama3.2
 *   <li>A short chat history (ChatMemory)
 *   <li>A verbose user query with historical context
 *   <li>A query transformation step to compress the query
 * </ul>
 *
 * <p>Use case: Helps improve retrieval in RAG by compressing verbose or redundant queries.
 */
public class CompressingQueryTransformerDemo {

  public static void main(String[] args) {

    // Step 1: Initialize the chat model (Ollama running locally)
    OllamaChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    // Step 2: Initialize chat memory with a windowed message history (up to 5
    // messages)
    ChatMemory chatMemory = MessageWindowChatMemory.builder().id("12345").maxMessages(5).build();

    // Add previous user and AI messages to simulate conversation context
    chatMemory.add(UserMessage.from("I'm researching electric vehicles."));
    chatMemory.add(AiMessage.from("Sure! What aspect of EVs are you interested in?"));

    // Step 3: Create the verbose user query
    String userQuery =
        "Can you help me find battery performance comparisons for EVs released after 2022?";

    // Attach metadata including user message and chat history
    Metadata metadata = Metadata.from(UserMessage.from(userQuery), 1, chatMemory.messages());
    Query originalQuery = Query.from(userQuery, metadata);

    // Step 4: Initialize the query transformer with the chat model
    CompressingQueryTransformer transformer = new CompressingQueryTransformer(chatModel);

    // Step 5: Transform the query using prior chat context
    Collection<Query> compressedQueries = transformer.transform(originalQuery);

    // Step 6: Output the compressed query (may be 1 or more if multi-query support
    // is enabled)
    compressedQueries.forEach(
        compressedQuery -> System.out.println("Compressed Query: " + compressedQuery.text()));
  }
}
