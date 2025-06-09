package com.sample.app.transformer;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.query.Metadata;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.rag.query.transformer.ExpandingQueryTransformer;
import java.util.Collection;

public class ExpandingQueryTransformerDemo {

  public static final int EXPECTED_QUERIES = 5;

  public static void main(String[] args) {

    // Step 1: Initialize the chat model (Ollama running locally)
    OllamaChatModel chatModel =
        OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2").build();

    // Step 2: Initialize chat memory with a windowed message history (up to 5 messages)
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

    // Step 4: Create ExpandingQueryTransformer with expected number of queries
    ExpandingQueryTransformer transformer =
        new ExpandingQueryTransformer(chatModel, EXPECTED_QUERIES);

    // Step 5: Transform the original query
    Collection<Query> expandedQueries = transformer.transform(originalQuery);

    // Step 6: Output the results
    System.out.println("Total Queries generated: " + expandedQueries.size());
    for (Query query : expandedQueries) {
      System.out.println(query.text());
    }
  }
}
