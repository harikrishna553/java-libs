package com.sample.documents.watcher;

import com.sample.documents.agent.FileSummaryAgent;
import com.sample.documents.model.DocumentSummary;
import com.sample.documents.perception.PerceptionResult;
import com.sample.documents.perception.PerceptionUtil;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Watches a directory for newly added documents.
 *
 * <p>The FileWatcher acts as the perception trigger of the AI Agent. Whenever a new file is
 * detected, it invokes the Perception Module, which extracts and normalizes the document before
 * passing it to the Foundation Model for summarization.
 */
public class FileWatcher {

  private final Path watchDirectory;
  private final PerceptionUtil perceptionUtil;
  private final FileSummaryAgent summaryAgent;

  public FileWatcher(
      Path watchDirectory, PerceptionUtil perceptionUtil, FileSummaryAgent summaryAgent) {

    this.watchDirectory = watchDirectory;
    this.perceptionUtil = perceptionUtil;
    this.summaryAgent = summaryAgent;
  }

  /** Starts monitoring the directory. */
  public void start() throws IOException, InterruptedException {

    if (!Files.exists(watchDirectory)) {
      Files.createDirectories(watchDirectory);
    }

    try (WatchService watchService = FileSystems.getDefault().newWatchService()) {

      watchDirectory.register(watchService, StandardWatchEventKinds.ENTRY_CREATE);

      System.out.println("=================================================");
      System.out.println("📂 Watching folder : " + watchDirectory.toAbsolutePath());
      System.out.println("Waiting for new documents...");
      System.out.println("=================================================");

      while (true) {

        WatchKey key = watchService.take();

        for (WatchEvent<?> event : key.pollEvents()) {

          if (event.kind() == StandardWatchEventKinds.OVERFLOW) {
            continue;
          }

          @SuppressWarnings("unchecked")
          WatchEvent<Path> pathEvent = (WatchEvent<Path>) event;

          Path file = watchDirectory.resolve(pathEvent.context());

          process(file);
        }

        if (!key.reset()) {
          break;
        }
      }
    }
  }

  /** Executes the perception pipeline and invokes the AI agent. */
  private void process(Path file) {

    try {

      // Wait briefly so the file copy operation completes.
      Thread.sleep(500);

      if (!Files.isRegularFile(file)) {
        return;
      }

      System.out.println();
      System.out.println("=================================================");
      System.out.println("📄 New File Detected");
      System.out.println("=================================================");
      System.out.println(file.getFileName());

      // -------------------------------
      // Perception Module
      // -------------------------------
      System.out.println();
      System.out.println("🧠 Perception Module");

      PerceptionResult result = perceptionUtil.perceive(file);

      System.out.println("✔ File Type : " + result.getFileType());
      System.out.println("✔ MIME Type : " + result.getMimeType());
      System.out.println("✔ Size      : " + result.getFileSize() + " bytes");
      System.out.println("✔ Content Extracted");
      System.out.println("✔ Prompt Constructed");

      // -------------------------------
      // Foundation Model
      // -------------------------------
      System.out.println();
      System.out.println("🤖 Foundation Model");
      System.out.println("Generating summary...");

      DocumentSummary summary = summaryAgent.chat(result.getNormalizedPrompt());

      // -------------------------------
      // Output
      // -------------------------------
      System.out.println();
      System.out.println("=================================================");
      System.out.println("📋 DOCUMENT SUMMARY");
      System.out.println("=================================================");
      System.out.println(summary);
      System.out.println("=================================================");

    } catch (Exception ex) {

      System.err.println("Failed to process file: " + file.getFileName());
      ex.printStackTrace();
    }
  }
}
