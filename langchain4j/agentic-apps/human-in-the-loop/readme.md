Build the application using Maven:

```bash
mvn clean package
```
Run the generated executable JAR:

```java
java -jar ./target/human-in-the-loop-1.0.0.jar
```
You can able to see following kind of messages in the console.

```java
$ java -jar ./target/human-in-the-loop-1.0.0.jar 

EMPLOYEE REQUEST
----------------
I need a MacBook Pro for application development.
The laptop costs approximately ₹175,000.
I need it for running containers, local LLMs,
development environments and performance tests.


=======================================
       MANAGER APPROVAL REQUIRED
=======================================

Item          : MacBook Pro
Amount        : ₹175000.0
Category      : IT Equipment
Risk          : HIGH
Justification : The laptop is required for running containers, local LLMs, development environments and performance tests, which are essential for the employee's job.

Approve this expense? (yes/no): yes

=======================================
            FINAL RESULT
=======================================
Expense Processing Result:

The expense request for the MacBook Pro has been approved by the manager.

Expense Details:

* Item: MacBook Pro
* Amount: $175,000.00
* Category: IT Equipment
* Risk Level: HIGH
* Justification: The laptop is required for running containers, local LLMs, development environments, and performance tests, which are essential for the employee's job.

Expense Status: Approved

Proceed with processing the expense.
```