Build the application using Maven:

```bash
mvn clean package
```
Run the generated executable JAR:

```java
java -jar ./target/observability-demo-1.0.0.jar 
```

You should see the application banner:

```bash
$ java -jar ./target/observability-demo-1.0.0.jar 

  ╔════════════════════════════════════════════════════╗
  ║           ✨   Story Teller Workflow   ✨            ║
  ╚════════════════════════════════════════════════════╝

  ℹ  Enter your story idea:
  You ❯ 

```
Enter a story idea when prompted. 
For example: Tell me story about a sage lived in India in Max 1000 characters