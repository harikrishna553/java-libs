package com.sample.app.document.loaders;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader;
import java.util.List;

public class FileSystemDocumentLoaderDemo {
  public static void main(String[] args) {
    List<Document> documents = FileSystemDocumentLoader.loadDocuments("/Users/Shared/llm_docs/");

    for (Document document : documents) {
      System.out.println(document.metadata());
    }
  }
}
