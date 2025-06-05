package com.sample.app.rag.apis.documents;

import java.util.HashMap;
import java.util.Map;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.Metadata;

public class DocumentHelloWorld {

	private static void printDocument(Document document) {
		System.out.println("Content : " + document.text());
		System.out.println("Metadata : " + document.metadata());
	}

	public static void main(String[] args) {
		Document doc1 = Document.from("Sample Document1");
		printDocument(doc1);

		Map<String, Object> doc2Metadata = new HashMap<>();
		doc2Metadata.put(Document.FILE_NAME, "langchain4j_tutorial.pdf");
		Document doc2 = Document.from("Langchain4j is easy to learn", new Metadata(doc2Metadata));
		printDocument(doc2);

	}

}
