package com.sample.documents;

import com.sample.documents.agent.AgentConfiguration;
import com.sample.documents.agent.FileSummaryAgent;
import com.sample.documents.perception.PerceptionUtil;
import com.sample.documents.watcher.FileWatcher;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Entry point for the Document Summary AI Agent.
 *
 * <p>This application demonstrates the Perception Module in an AI Agent. It continuously monitors a
 * folder for newly added documents. When a document arrives, the following pipeline is executed:
 *
 * <pre>
 * New Document
 *       │
 *       ▼
 * File Watcher
 *       │
 *       ▼
 * Perception Module
 *       │
 *       ▼
 * Foundation Model (LLM)
 *       │
 *       ▼
 * Structured Document Summary
 * </pre>
 */
public class Application {

  /** Folder monitored for incoming documents. */
  private static Path WATCH_DIRECTORY = null;

  public static void main(String[] args) throws Exception {

    System.out.println("===============================================");
    System.out.println("      Document Summary AI Agent");
    System.out.println("===============================================");
    System.out.println();

    if (args[0] == null) {
      System.out.println("Watch Directory Path is not Set");
      System.exit(1);
    }

    WATCH_DIRECTORY = Paths.get(args[0]);
    // WATCH_DIRECTORY = Paths.get("/Users/Shared/files");

    // Create the Foundation Model (AI Agent)
    FileSummaryAgent summaryAgent = AgentConfiguration.createFileSummaryAgent();

    // Create the Perception Module
    PerceptionUtil perceptionUtil = new PerceptionUtil();

    // Create the File Watcher
    FileWatcher watcher = new FileWatcher(WATCH_DIRECTORY, perceptionUtil, summaryAgent);

    System.out.println("AI Agent Initialized");
    System.out.println("Perception Module Ready");
    System.out.println("Watching folder: " + WATCH_DIRECTORY.toAbsolutePath());
    System.out.println();
    System.out.println("Drop a PDF, DOCX, TXT, JSON or Markdown file into the folder...");
    System.out.println();

    // Start monitoring the folder
    watcher.start();
  }
}
