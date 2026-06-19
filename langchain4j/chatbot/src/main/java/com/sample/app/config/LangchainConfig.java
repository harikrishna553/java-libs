package com.sample.app.config;

import com.sample.app.assistant.ChatAssistant;
import com.sample.app.tools.TimeMachineRagTool;
import com.sample.app.tools.TimeTravelBusinessTools;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.ollama.OllamaLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.store.memory.chat.InMemoryChatMemoryStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.URL;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;

@Configuration
public class LangchainConfig {

    private static final Logger log = LoggerFactory.getLogger(LangchainConfig.class);

    @Value("${ollama.base-url}")
    private String baseUrl;

    @Value("${ollama.chat-model}")
    private String chatModelName;

    @Value("${ollama.temperature}")
    private Double temperature;

    @Value("${ollama.timeout-seconds}")
    private Long timeoutSeconds;

    // ── Legacy LanguageModel (kept for backward compatibility) ───────────────
    @Bean
    public OllamaLanguageModel ollamaLanguageModel() {
        return OllamaLanguageModel.builder()
                .baseUrl(baseUrl)
                .modelName(chatModelName)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }

    // ── Chat Model (used by AiServices / ChatAssistant) ──────────────────────
    @Bean
    public OllamaChatModel ollamaChatModel() {
        return OllamaChatModel.builder()
                .baseUrl(baseUrl)
                .modelName(chatModelName)
                .temperature(temperature)
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .build();
    }

    // ── In-Memory Embedding Store ─────────────────────────────────────────────
    // easy-rag's EmbeddingStoreIngestor uses the bundled AllMiniLM-L6-v2 model —
    // no Ollama embedding model required.
    @Bean
    public InMemoryEmbeddingStore<TextSegment> embeddingStore() {
        InMemoryEmbeddingStore<TextSegment> store = new InMemoryEmbeddingStore<>();

        try {
            URL pdfUrl = getClass().getClassLoader().getResource("timemachine.pdf");
            if (pdfUrl == null) {
                log.warn("timemachine.pdf not found in classpath — RAG tool will return empty results");
                return store;
            }

            log.info("Loading timemachine.pdf...");
            // FileSystemDocumentLoader auto-detects .pdf via easy-rag's bundled parsers
            Document document = FileSystemDocumentLoader.loadDocument(Paths.get(pdfUrl.toURI()));
            List<Document> documents = List.of(document);

            // EmbeddingStoreIngestor handles splitting + embedding internally
            EmbeddingStoreIngestor.ingest(documents, store);

            log.info("Ingested timemachine.pdf into InMemoryEmbeddingStore");

        } catch (Exception e) {
            log.error("Failed to load/ingest timemachine.pdf: {}", e.getMessage(), e);
        }

        return store;
    }

    // ── RAG Tool (wraps EmbeddingStoreContentRetriever as an agent tool) ──────
    @Bean
    public TimeMachineRagTool timeMachineRagTool(InMemoryEmbeddingStore<TextSegment> embeddingStore) {
        return new TimeMachineRagTool(embeddingStore);
    }

    // ── Shared chat memory store (keyed by memoryId per request) ─────────────
    @Bean
    public InMemoryChatMemoryStore chatMemoryStore() {
        return new InMemoryChatMemoryStore();
    }

    // ── ChatAssistant (AiServices agent with tools + per-request memory) ──────
    @Bean
    public ChatAssistant chatAssistant(OllamaChatModel chatModel,
                                       TimeMachineRagTool ragTool,
                                       TimeTravelBusinessTools businessTools,
                                       InMemoryChatMemoryStore chatMemoryStore) {
        return AiServices.builder(ChatAssistant.class)
                .chatModel(chatModel)
                .tools(ragTool, businessTools)
                .chatMemoryProvider(memoryId -> MessageWindowChatMemory.builder()
                        .id(memoryId)
                        .maxMessages(50)
                        .chatMemoryStore(chatMemoryStore)
                        .build())
                .build();
    }
}
