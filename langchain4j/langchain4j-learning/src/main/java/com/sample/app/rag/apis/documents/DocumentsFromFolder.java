package com.sample.app.rag.apis.documents;

import java.util.List;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;

public class DocumentsFromFolder {
	public static void main(String[] args) {
		String resourcesFolderPath = "/Users/Shared/llm_docs";
		List<Document> documents = FileSystemDocumentLoader.loadDocuments(resourcesFolderPath);

		for (Document doc : documents) {
			System.out.println("------------------------------------------");
			System.out.println("File name : " + doc.text());
			System.out.println("Metadata : " + doc.metadata());
			System.out.println("------------------------------------------\n");
		}
	}

}
