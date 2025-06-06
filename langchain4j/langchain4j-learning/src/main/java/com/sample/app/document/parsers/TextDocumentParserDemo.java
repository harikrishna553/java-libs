package com.sample.app.document.parsers;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import dev.langchain4j.data.document.parser.TextDocumentParser;

public class TextDocumentParserDemo {

	public static void main(String[] args) {
		Document document = FileSystemDocumentLoader.loadDocument("/Users/Shared/llm_docs/test.txt",
				new TextDocumentParser());

		System.out.println(document.text());
	}

}
