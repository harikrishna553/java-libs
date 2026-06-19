package com.sample.app.tools;

import dev.langchain4j.agent.tool.Tool;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.rag.content.Content;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.query.Query;
import dev.langchain4j.store.embedding.EmbeddingStore;

import java.util.List;
import java.util.stream.Collectors;

/**
 * RAG tool backed by the in-memory embedding store populated from timemachine.pdf.
 * Uses easy-rag's bundled AllMiniLM-L6-v2 embedding model — no Ollama required for retrieval.
 */
public class TimeMachineRagTool {

    private final ContentRetriever contentRetriever;

    public TimeMachineRagTool(EmbeddingStore<TextSegment> embeddingStore) {
        // No embeddingModel() call — EmbeddingStoreContentRetriever picks up the same
        // default AllMiniLM model that EmbeddingStoreIngestor used during ingestion.
        this.contentRetriever = EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .maxResults(5)
                .minScore(0.75)
                .build();
    }

    @Tool("""
            Searches the TimeMachine Corp official knowledge base and documentation for information about
            time machines, how time travel works, safety protocols, trip details, historical destinations,
            company history, FAQs, and any other time-travel-related topics.
            Always call this tool first whenever the user asks anything about time machines or time travel.
            Returns the most relevant document excerpts matching the query.
            """)
    public String searchTimeMachineKnowledge(String query) {
        System.out.println("*** Searching Time Machine Knowledge Base: " + query + " ***");

        List<Content> contents = contentRetriever.retrieve(Query.from(query));

        if (contents.isEmpty()) {
            return "No relevant information found in the TimeMachine Corp documentation for: \"" + query + "\".";
        }

        return contents.stream()
                .map(content -> content.textSegment().text())
                .collect(Collectors.joining("\n\n---\n\n"));
    }
}
