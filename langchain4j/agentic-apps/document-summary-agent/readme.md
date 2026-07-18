# Execution Flow

```text
                 Application
                      │
                      ▼
          Creates AI Agent Components
                      │
                      ▼
                FileWatcher
          (Perception Trigger)
                      │
          New Document Arrives
                      │
                      ▼
              PerceptionUtil
        (Detect • Extract • Normalize)
                      │
                      ▼
            PerceptionResult
      (Unified Prompt + Metadata)
                      │
                      ▼
            FileSummaryAgent
        (Foundation Model / LLM)
                      │
                      ▼
            DocumentSummary
                      │
                      ▼
            Display Summary
```

---

# Build the Project

Open a terminal and navigate to the directory containing the `pom.xml` file.

Build the project using Maven:

```bash
mvn clean package
```

After a successful build, the executable JAR will be generated in the `target` directory.

```
target/
└── document-summary-agent-0.0.1-SNAPSHOT.jar
```

---

# Run the Application

Start the application by providing the folder that the agent should monitor.

```bash
java -jar ./target/document-summary-agent-0.0.1-SNAPSHOT.jar {FOLDER_TO_WATCH}
```

### Example

```bash
java -jar ./target/document-summary-agent-0.0.1-SNAPSHOT.jar /Users/Shared/files
```

---

# Test the Agent

Once the application starts successfully, it begins monitoring the specified directory.

Simply copy or move any supported document into the watched folder.

For example:

- PDF (`.pdf`)
- Microsoft Word (`.doc`, `.docx`)
- Text (`.txt`)
- JSON (`.json`)
- Markdown (`.md`)
- HTML (`.html`)
- XML (`.xml`)

As soon as a new document appears, the AI agent automatically executes the following workflow:

1. **FileWatcher** detects the newly added document.
2. **PerceptionUtil** identifies the document type.
3. The Perception Module extracts metadata and textual content using Apache Tika.
4. The extracted content is normalized into a unified prompt.
5. The normalized prompt is sent to the **Foundation Model (LLM)**.
6. The LLM generates a structured `DocumentSummary`.
7. The summary is displayed in the console.

No manual interaction is required—the entire perception and summarization pipeline is event-driven.

---

# Sample Console Output

```text
===============================================
      Document Summary AI Agent
===============================================

AI Agent Initialized
Perception Module Ready
Watching folder: /Users/Shared/files

Drop a PDF, DOCX, TXT, JSON or Markdown file into the folder...

=================================================
📂 Watching folder : /Users/Shared/files
Waiting for new documents...
=================================================

=================================================
📄 New File Detected
=================================================
AI-Agent-Architecture.txt

🧠 Perception Module

✔ File Type : TEXT
✔ MIME Type : text/plain
✔ Content Extracted
✔ Prompt Constructed

🤖 Foundation Model

Generating summary...

=================================================
📋 DOCUMENT SUMMARY
=================================================

Executive Summary
...

Key Topics
...

Important Points
...

Action Items
...

Conclusion
...
```