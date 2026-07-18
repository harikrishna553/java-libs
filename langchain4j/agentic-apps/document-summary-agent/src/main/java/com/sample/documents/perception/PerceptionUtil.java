package com.sample.documents.perception;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.apache.tika.Tika;
import org.apache.tika.langdetect.optimaize.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

/**
 * Perception Module.
 *
 * <p>Responsible for:
 *
 * <ul>
 *   <li>Detecting document type
 *   <li>Extracting metadata
 *   <li>Extracting textual content
 *   <li>Normalizing the extracted content
 *   <li>Building a prompt for the Foundation Model
 * </ul>
 */
public class PerceptionUtil {

  private final Tika tika = new Tika();

  /**
   * Perceives a document and produces a normalized prompt.
   *
   * @param file document path
   * @return perception result
   */
  public PerceptionResult perceive(Path file) {

    try {

      Metadata metadata = new Metadata();
      ContentHandler handler = new BodyContentHandler(-1);
      AutoDetectParser parser = new AutoDetectParser();

      ParseContext context = new ParseContext();

      try (InputStream inputStream = Files.newInputStream(file)) {
        parser.parse(inputStream, handler, metadata, context);
      }

      String content = handler.toString();

      System.out.println("Content length = " + content.length());
      System.out.println(content);

      PerceptionResult result = new PerceptionResult();

      result.setFileName(file.getFileName().toString());
      result.setFileSize(Files.size(file));
      result.setMimeType(tika.detect(file));
      result.setFileType(detectFileType(file));
      result.setTitle(metadata.get("title"));
      result.setAuthor(metadata.get("Author"));
      result.setLanguage(detectLanguage(content));
      result.setContent(content);

      result.setNormalizedPrompt(buildPrompt(result));

      return result;

    } catch (Exception ex) {
      throw new RuntimeException("Failed to perceive document: " + file, ex);
    }
  }

  /** Detects the supported file type. */
  private SupportedFileType detectFileType(Path file) {

    String name = file.getFileName().toString().toLowerCase();

    if (name.endsWith(".pdf")) {
      return SupportedFileType.PDF;
    }

    if (name.endsWith(".doc") || name.endsWith(".docx")) {
      return SupportedFileType.WORD;
    }

    if (name.endsWith(".txt")) {
      return SupportedFileType.TEXT;
    }

    if (name.endsWith(".json")) {
      return SupportedFileType.JSON;
    }

    if (name.endsWith(".md")) {
      return SupportedFileType.MARKDOWN;
    }

    if (name.endsWith(".html") || name.endsWith(".htm")) {
      return SupportedFileType.HTML;
    }

    if (name.endsWith(".xml")) {
      return SupportedFileType.XML;
    }

    return SupportedFileType.UNKNOWN;
  }

  /** Detects the language of the extracted content. */
  private String detectLanguage(String text) throws IOException {

    if (text == null || text.isBlank()) {
      return "Unknown";
    }

    LanguageDetector detector = new OptimaizeLangDetector().loadModels();

    return detector.detect(text).getLanguage();
  }

  /** Builds the normalized prompt that will be sent to the LLM. */
  private String buildPrompt(PerceptionResult result) {

    StringBuilder builder = new StringBuilder();

    builder.append("Document Information\n");
    builder.append("--------------------\n");
    builder.append("File Name : ").append(result.getFileName()).append('\n');
    builder.append("Document Type : ").append(result.getFileType()).append('\n');
    builder.append("MIME Type : ").append(result.getMimeType()).append('\n');
    builder.append("Language : ").append(result.getLanguage()).append('\n');

    if (result.getTitle() != null) {
      builder.append("Title : ").append(result.getTitle()).append('\n');
    }

    if (result.getAuthor() != null) {
      builder.append("Author : ").append(result.getAuthor()).append('\n');
    }

    builder.append("\n");
    builder.append("Document Content\n");
    builder.append("----------------\n");
    builder.append(result.getContent());

    return builder.toString();
  }
}
