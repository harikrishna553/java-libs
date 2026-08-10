## Setup and Run

Build the application using Maven:

```bash
mvn clean package
```
Run the generated executable JAR:

```java
java -jar ./target/sequential-handoff-pattern-untyped-agents-1.0.0.jar
```

You should see the application banner:

```bash
╔════════════════════════════════════════════════════╗
║           ✨   Story Teller Workflow   ✨            ║
╚════════════════════════════════════════════════════╝

ℹ  Enter your story idea:
You ❯
```
Enter a story idea when prompted. 
For example: Tell me story about a sage lived in India