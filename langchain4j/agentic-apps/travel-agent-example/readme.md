# AI Travel Booking Agent (Java + LangChain4j + Ollama)

A simple educational AI Agent demonstrating how the core architectural components of an autonomous agent work together to solve a real-world problem.

> **Note**
>
> This project is **not** a production-ready travel booking application. It uses mocked tools and hardcoded travel data to demonstrate AI agent architecture. The goal is to help developers understand how modern AI agents are designed rather than how airline or hotel APIs work.

---

# Overview

Throughout this series, we explored each major component of an AI Agent individually:

- Perception Module
- Planning Module
- Agent Memory
- Action Module
- Tool Registry
- Agent Orchestrator

Understanding each module separately is important, but the real power of an AI agent comes from seeing how all of these components collaborate together.

This project combines every module into a complete working example using a **Travel Booking Agent**.

Given a request such as:

```
Book a 5-day trip to London
```

the agent will autonomously perform multiple reasoning and execution cycles to complete the booking.

The agent is capable of:

- Understanding the user's travel request
- Planning the next action
- Searching flights
- Asking the user for missing information
- Booking flights
- Searching hotels
- Booking hotels
- Sending booking confirmation emails
- Suggesting local tour plans

Although every tool is implemented using mocked data, the architecture closely resembles a production AI agent.

Replacing the mocked implementations with real APIs would require minimal architectural changes.

---

# Project Goals

The primary objective of this project is educational.

After studying this project you should understand:

- How AI agents think
- How agents decide what to do next
- How memory influences reasoning
- How tools extend an LLM's capabilities
- How the ReAct loop operates
- How different architectural modules collaborate

---

# Architecture

The Travel Booking Agent consists of six major components.

```
                   User
                     │
                     ▼
            Perception Module
                     │
                     ▼
                   Goal
                     │
                     ▼
            Agent Orchestrator
                     │
        ┌────────────┴────────────┐
        │                         │
        ▼                         ▼
 Planning Module            Agent Memory
        │                         ▲
        ▼                         │
   Plan (Next Step)               │
        │                         │
        ▼                         │
    Action Module ────────────────┘
        │
        ▼
    Tool Registry
        │
        ├── Flight Search
        ├── Flight Booking
        ├── Hotel Search
        ├── Hotel Booking
        ├── Email
        ├── Ask User
        └── Tour Suggestion
```

Each module has a single responsibility, making the application modular, testable, and easy to extend.

---

# Project Modules

## 1. Goal

Every AI agent needs a well-defined objective.

Instead of allowing every module to process raw user input independently, the application converts user requests into a shared `Goal` object.

```java
public record Goal(String description) {}
```

Example

User Input

```
Book my trip to London
```

Goal

```
Book my trip to London
```

Every other module now works with the structured goal rather than raw text.

---

## 2. Perception Module

The Perception Module is responsible for understanding user input.

Its responsibilities include:

- Reading user input
- Cleaning the text
- Normalizing the request
- Producing a Goal object

```
User Input
      │
      ▼
Perception Module
      │
      ▼
     Goal
```

In production systems this module may additionally perform:

- Intent detection
- Entity extraction
- Language translation
- Spell correction
- OCR
- Speech recognition
- Image understanding
- Sentiment analysis

This demo keeps the implementation intentionally simple.

---

## 3. Planning Module

Planning determines **what should happen next**.

It never performs the work itself.

Instead it asks the language model:

> Given the current goal, memory and available tools, what should I do next?

The Planning Module consists of:

- LlmClient
- PlanParser

```
Planning Module
        │
        ▼
    LlmClient
        │
        ▼
      Ollama
```

The LLM returns structured JSON which is converted into:

```java
public record PlanResponse(
    String status,
    PlanStep nextStep
) {}
```

Each plan contains a single executable step.

```java
public record PlanStep(
    String toolName,
    Map<String,String> parameters,
    String reasoning
) {}
```

Example

```
Tool:
FlightSearchTool

Parameters

destination=London
days=5

Reason

Search available flights before booking.
```

The planner therefore acts as the **brain** of the agent.

---

## 4. Agent Memory

Memory stores everything the agent has learned so far.

Without memory the planner would repeatedly ask the same questions or perform the same actions.

Example state:

```java
Goal

Available Flights

Available Hotels

Selected Flight

Selected Hotel

Flight Booked

Hotel Booked

Email Sent

Tour Suggested
```

Every tool updates memory after execution.

```
Flight Search
      │
      ▼
Available Flights Updated

Flight Booking
      │
      ▼
Flight Booked = true

Email Tool
      │
      ▼
Email Sent = true
```

Every planning cycle uses the latest memory.

---

## 5. Action Module

Planning decides.

Action executes.

Responsibilities include:

- Receive PlanStep
- Locate Tool
- Execute Tool
- Update Memory
- Return Observation

```
Plan
   │
   ▼
Action Module
   │
   ▼
Tool
```

The planner never directly calls tools.

The Action Module never performs reasoning.

This separation keeps responsibilities clean.

---

## 6. Tool Registry

Tools provide the agent with capabilities.

Each tool implements the same interface.

```java
public interface Tool {

    String getName();

    String getDescription();

    String getParameterDescription();

    ActionResult execute(
        Map<String,String> params,
        AgentMemory memory
    );
}
```

Because every tool follows the same contract, the Action Module can invoke any tool dynamically.

---

# Available Tools

| Tool | Purpose |
|-------|---------|
| AskUserTool | Collect missing information |
| FlightSearchTool | Search available flights |
| FlightBookTool | Book selected flight |
| HotelSearchTool | Search available hotels |
| HotelBookTool | Book selected hotel |
| EmailTool | Send booking confirmation |
| SuggestTourPlansTool | Recommend local tour plans |

Each tool performs only one responsibility, making it reusable and independently testable.

---

## Tool Registry

Rather than hardcoding tool implementations, all tools are registered centrally.

```
Tool Registry
      │
      ├── FlightSearchTool
      ├── FlightBookTool
      ├── HotelSearchTool
      ├── HotelBookTool
      ├── EmailTool
      ├── AskUserTool
      └── SuggestTourPlansTool
```

Adding a new tool simply requires registration.

No changes are required in the Action Module.

---

## 7. Agent Orchestrator

The Agent Orchestrator coordinates the complete ReAct loop.

It is responsible for:

- Storing the goal
- Calling the planner
- Executing planned actions
- Updating memory
- Repeating until completion

```
Store Goal
     │
     ▼
Plan Next Action
     │
     ▼
Execute Tool
     │
     ▼
Update Memory
     │
     ▼
Completed?
     │
 ┌───┴────┐
 │        │
 No      Yes
 │        │
 ▼        ▼
Repeat   Finish
```

The orchestrator also performs deterministic checks.

Instead of relying only on the LLM to determine completion, it verifies business conditions such as:

- Flight booked
- Hotel booked
- Confirmation email sent

This improves reliability and prevents unnecessary planning iterations.

---

# ReAct Loop

The complete reasoning cycle looks like this.

```
User Goal
     │
     ▼
Perception
     │
     ▼
Planning
     │
     ▼
Action
     │
     ▼
Observation
     │
     ▼
Memory Updated
     │
     ▼
Planning Again
```

This continues until the planner reports completion.

---

# Project Structure

```
travel-agent-example
│
├── perception
├── planning
├── memory
├── action
├── orchestrator
├── tools
│
├── llm
├── model
├── util
│
└── TravelAgentApplication.java
```

---

# Prerequisites

Before running the project, install:

- Java 21 or later
- Maven 3.9+
- Ollama

Download the required model:

```
ollama pull llama3.2
```

Verify installation:

```
ollama list
```

---

# Build the Project

Navigate to the project root containing `pom.xml`.

Build using Maven.

```bash
mvn clean package
```

After a successful build, Maven generates the JAR file inside the `target` directory.

Example:

```
target/

classes/
generated-sources/
generated-test-sources/
maven-archiver/
maven-status/
original-travel-agent-example-1.0.0.jar
travel-agent-example-1.0.0.jar
```

The executable application is:

```
travel-agent-example-1.0.0.jar
```

---

# Run the Application

Execute:

```bash
java -jar target/travel-agent-example-1.0.0.jar
```

---

# Sample Input

When prompted, enter:

```
Book a 5-day trip to London
```

Example interaction:

```
User:
Book a 5-day trip to London

Agent:
Searching available flights...

Agent:
Found 3 flights.

Agent:
Please select a flight.

User:
AA-303

Agent:
Searching hotels...

Agent:
Found 5 hotels.

User:
H-001

Agent:
Booking completed.

Agent:
Confirmation email sent.

Agent:
Here are some recommended tour plans.
```

---

# Learning Outcomes

After completing this project, you will understand:

- AI Agent architecture
- ReAct reasoning loops
- Planning vs execution
- Tool calling
- Memory management
- Orchestration
- Modular agent design
- Extending agents with new tools
- Replacing mocked tools with real-world APIs

---

# Future Enhancements

Some possible improvements include:

- Real airline APIs
- Real hotel booking APIs
- Payment gateway integration
- Weather APIs
- Maps integration
- Calendar integration
- Voice input
- Multi-agent collaboration
- Persistent memory using databases
- Vector database integration
- Retrieval-Augmented Generation (RAG)
- Human approval workflows
- Tool permission management
- Observability and tracing
- Authentication and authorization

---

# License

This project is intended for educational purposes to demonstrate AI Agent architecture using Java, LangChain4j, and Ollama.