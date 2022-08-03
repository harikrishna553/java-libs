# Compile the project
I tested this application with Java15, set Java home.

```
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk-15.0.2.jdk/Contents/Home/
```

Execute the command 'mvn clean install' to generate the artifact.

# Run the application class
java -jar ./target/jmh-demo-1.jar {CLASS_NAME} (OR)
		
java -jar ./target/jmh-demo-1.jar {CLASS_NAME} -f 1 