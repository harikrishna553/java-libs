package com.sample.app.model;

import dev.langchain4j.model.output.structured.Description;

public class Document {
  @Description("Document Title")
  private String title;

  @Description("Brief Summary of the Document")
  private String content;

  public Document(String title, String content) {
    this.title = title;
    this.content = content;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  @Override
  public String toString() {
    return "Document [title=" + title + ", content=" + content + "]";
  }
}
