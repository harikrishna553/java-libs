package com.sample.app;

import java.util.ArrayList;
import java.util.List;

import com.sample.app.assistants.ChatAssistant;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;

public class InMemoryRag {

	public static void main(String[] args) {

		// Load all the Documents
		String resourcesFolderPath = "/Users/Shared/llm_docs";
		System.out.println("Resources Folder Path is " + resourcesFolderPath);
		List<Document> documents = FileSystemDocumentLoader.loadDocuments(resourcesFolderPath);

		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		EmbeddingStoreIngestor.ingest(documents, embeddingStore);

		OllamaChatModel chatModel = OllamaChatModel.builder().baseUrl("http://localhost:11434").modelName("llama3.2")
				.build();

		ChatAssistant assistant = AiServices.builder(ChatAssistant.class).chatModel(chatModel)
				.contentRetriever(EmbeddingStoreContentRetriever.from(embeddingStore)).build();

		List<String> questionsToAsk = new ArrayList<>();
		questionsToAsk.add("What is the tag line of ChronoCore Industries?");

		long time1 = System.currentTimeMillis();
		for (String question : questionsToAsk) {
			String answer = assistant.chat(question);
			System.out.println("----------------------------------------------------");
			System.out.println("Q: " + question);
			System.out.println("A : " + answer);
			System.out.println("----------------------------------------------------\n");
		}
		long time2 = System.currentTimeMillis();

		System.out.println("Total time taken is " + (time2 - time1));
	}

}


