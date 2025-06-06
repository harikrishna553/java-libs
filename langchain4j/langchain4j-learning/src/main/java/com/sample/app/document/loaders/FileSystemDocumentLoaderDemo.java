package com.sample.app.document.loaders;

import java.util.List;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;

public class FileSystemDocumentLoaderDemo {
	public static void main(String[] args) {
		List<Document> documents = FileSystemDocumentLoader.loadDocuments("/Users/Shared/llm_docs/");
		
		for(Document document: documents) {
			System.out.println(document.metadata());
		}
	}

}
