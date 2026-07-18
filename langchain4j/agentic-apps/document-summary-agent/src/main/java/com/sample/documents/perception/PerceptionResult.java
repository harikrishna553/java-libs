package com.sample.documents.perception;

/**
 * Represents the output of the Perception Module.
 *
 * <p>This object contains everything extracted from the incoming document, including metadata, raw
 * content, and the final normalized prompt that will be sent to the Foundation Model.
 */
public class PerceptionResult {

  private String fileName;

  private SupportedFileType fileType;

  private String mimeType;

  private long fileSize;

  private String title;

  private String author;

  private String language;

  private String content;

  private String normalizedPrompt;

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public SupportedFileType getFileType() {
    return fileType;
  }

  public void setFileType(SupportedFileType fileType) {
    this.fileType = fileType;
  }

  public String getMimeType() {
    return mimeType;
  }

  public void setMimeType(String mimeType) {
    this.mimeType = mimeType;
  }

  public long getFileSize() {
    return fileSize;
  }

  public void setFileSize(long fileSize) {
    this.fileSize = fileSize;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getLanguage() {
    return language;
  }

  public void setLanguage(String language) {
    this.language = language;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getNormalizedPrompt() {
    return normalizedPrompt;
  }

  public void setNormalizedPrompt(String normalizedPrompt) {
    this.normalizedPrompt = normalizedPrompt;
  }
}
