Build the application using Maven:

```bash
mvn clean package
```
Run the generated executable JAR by simulating failure:

```java
java -jar ./target/cross-agent-compensation-demo-1.0.0.jar --simulate-failure
```

Run the generated executable JAR:

```java
java -jar ./target/cross-agent-compensation-demo-1.0.0.jar
```
