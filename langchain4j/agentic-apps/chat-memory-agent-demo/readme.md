## Build the Project

Open a terminal and navigate to the directory containing the `pom.xml` file. Then build the project using the following command:

```bash
mvn clean package
```

---

## Run the Application

Once the build completes successfully, the executable JAR file will be generated in the `target` directory.

Start the application using the following command:

```bash
java -jar ./target/chat-memory-agent-demo-1.0.0.jar 
```

If everything is configured correctly, the application will start and you can see following messages in the console

```bash
$ java -jar ./target/chat-memory-agent-demo-1.0.0.jar 
=== @ChatMemorySupplier Demo ===

SharedMemorySupportAgent -> Creating shared ChatMemory
User  : My name is Chamu and I am from Bangalore.
Agent : Hello Chamu! It's nice to meet you. I don't have any prior information about you, but I'm happy to chat with you now that we've established your name and location. You're from Bangalore, which is a beautiful city in India. Is there something specific you'd like to talk about or ask about?

User  : What is my name and where am I from?
Agent : You mentioned earlier that your name is Chamu, and you are from Bangalore.

========================================

=== @ChatMemoryProviderSupplier Demo ===

--- Customer 101 ---
CustomerMemorySupportAgent -> Creating memory for: CUSTOMER-101
User  : My name is Chamu and I am interested in a laptop.
Agent : Hello Chamu! Nice to meet you. I'd be happy to help you find a laptop that suits your needs.

Before we get started, can you please tell me a bit more about what you're looking for? For example:

* What will you be using the laptop for (work, gaming, general use)?
* Do you have a specific budget in mind?
* Are there any particular features or specifications you're interested in (e.g. touchscreen, high-performance processor, long battery life)?

Let me know and I'll do my best to guide you through our options!

--- Customer 202 ---
CustomerMemorySupportAgent -> Creating memory for: CUSTOMER-202
User  : My name is Ravi and I am interested in a mobile phone.
Agent : Hello Ravi! Nice to meet you. I'd be happy to help you with your mobile phone inquiry.

Can you please tell me what kind of phone you're looking for? Are you interested in a specific brand, model, or features such as camera quality, battery life, or storage capacity?

Also, do you have any budget in mind for the purchase?

--- Customer 101 Follow-up ---
User  : What is my name and what product am I interested in?
Agent : Your name is Chamu, and you are interested in a laptop.

--- Customer 202 Follow-up ---
User  : What is my name and what product am I interested in?
Agent : Your name is Ravi, and you are interested in a mobile phone.
```
