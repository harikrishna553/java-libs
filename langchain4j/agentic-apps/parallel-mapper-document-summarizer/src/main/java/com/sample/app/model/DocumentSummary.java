package com.sample.app.model;

public class DocumentSummary {
  private String documentTitle;
  private String summary;

  public String getDocumentTitle() {
    return documentTitle;
  }

  public void setDocumentTitle(String documentTitle) {
    this.documentTitle = documentTitle;
  }

  public String getSummary() {
    return summary;
  }

  public void setSummary(String summary) {
    this.summary = summary;
  }

  @Override
  public String toString() {
    return "DocumentSummary{"
        + "documentTitle='"
        + documentTitle
        + '\''
        + ", summary='"
        + summary
        + '\''
        + '}';
  }
}
