qwen3.5# TimeMachine Corp — AI Chatbot API

A LangChain4j + Spring Boot chatbot powered by Ollama (llama3.2) with:
- **Agentic tool use** — business metric tools (revenue, price, headcount, etc.)
- **In-memory RAG** — `timemachine.pdf` embedded at startup via `langchain4j-easy-rag`
- **Stateless conversation** — client owns and sends full message history each request
- **DeepEval-ready responses** — `toolCalls` (name, description, input, output) included in every response

---

## Prerequisites

| Requirement | Version |
|---|---|
| Java | 21+ |
| Maven | 3.8+ |
| [Ollama](https://ollama.ai) | latest |

### Pull required Ollama models

```bash
ollama pull qwen3.5
```

---

## Running the App

```bash
# 1. Start Ollama
ollama serve

# 2. Build and run the Spring Boot app
cd chatbot
mvn spring-boot:run
```

Server starts on **http://localhost:8080**

- Swagger UI → http://localhost:8080/swagger-ui.html
- OpenAPI spec → http://localhost:8080/v3/api-docs

> On first startup the app loads and embeds `timemachine.pdf` — expect ~10–30 seconds before the first request.

---

## API Reference

### `POST /api/chat/simple` — Single message

Send one message, get a reply. No history needed.

**Request**
```json
{
  "message": "How many people are time traveling right now?"
}
```

**Response**
```json
{
  "role": "assistant",
  "content": "Currently, 142 people are actively time traveling!",
  "toolCalls": [
    {
      "name": "currentPeopleInTimeTravel",
      "description": "Returns the current number of people who are actively time traveling.",
      "input": {},
      "output": 142
    }
  ]
}
```

---

**RAG example** — questions answered from `timemachine.pdf`

```bash
curl -s -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "What is a time machine?"}'
```

---

**Off-topic question** — agent politely declines

```bash
curl -s -X POST http://localhost:8080/api/chat/simple \
  -H "Content-Type: application/json" \
  -d '{"message": "What is the capital of France?"}'
```

```json
{
  "role": "assistant",
  "content": "I'm sorry, I'm specialized exclusively in time travel services and TimeMachine Corp operations. I'm unable to assist with that topic. Is there anything about time travel or our business I can help you with? 🕰️"
}
```

---

### `POST /api/chat` — Conversation (full history)

Send the full message history. The **last entry must have `role: "user"`**.  
Prior turns provide conversation context — the server is stateless and does not store history.

**Request format**
```json
[
  { "role": "user",      "content": "..." },
  { "role": "assistant", "content": "..." },
  { "role": "user",      "content": "..." }
]
```

---

#### Example 1 — Memory across turns (name recall)

The agent remembers the user's name from earlier in the conversation.

```bash
curl -s -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '[
    {
      "role": "user",
      "content": "Hi, My name is Harikrishna"
    },
    {
      "role": "assistant",
      "content": "Hello! How can I help you today?"
    },
    {
      "role": "user",
      "content": "I want to learn about time machines"
    },
    {
      "role": "assistant",
      "content": "Time machines are great"
    },
    {
      "role": "user",
      "content": "Before we go deeper, what'\''s my name?"
    }
  ]'
```

**Response**
```json
{
  "role": "assistant",
  "content": "Your name is Harikrishna! Now, shall we dive deeper into the fascinating world of time machines? 🕰️"
}
```

> No tools are called here — the agent answers directly from conversation context.

---

#### Example 2 — Tool call within a conversation

The agent calls the `currentPeopleInTimeTravel` tool mid-conversation.

```bash
curl -s -X POST http://localhost:8080/api/chat \
  -H "Content-Type: application/json" \
  -d '[
    {
      "role": "user",
      "content": "Hi, My name is Harikrishna"
    },
    {
      "role": "assistant",
      "content": "Hello! How can I help you today?"
    },
    {
      "role": "user",
      "content": "I want to learn about time machines"
    },
    {
      "role": "assistant",
      "content": "Time machines are great"
    },
    {
      "role": "user",
      "content": "How many people are on the time machine now?"
    }
  ]'
```

**Response**
```json
{
  "role": "assistant",
  "content": "Currently, there are 142 people actively time traveling right now!",
  "toolCalls": [
    {
      "name": "currentPeopleInTimeTravel",
      "description": "Returns the current number of people who are actively time traveling.",
      "input": {},
      "output": 142
    }
  ]
}
```

---

## Available Tools

| Tool | Description | Returns |
|---|---|---|
| `searchTimeMachineKnowledge` | Searches `timemachine.pdf` via RAG | Relevant document chunks |
| `currentPeopleInTimeTravel` | People actively time traveling right now | `int` |
| `totalRevenueFromTimeTravel` | Total revenue in USD | `double` |
| `totalCost` | Total operational costs in USD | `double` |
| `currentMarketPrice` | Ticket price per trip in USD | `double` |
| `netProfit` | Revenue minus costs in USD | `double` |
| `totalTripsCompleted` | Total trips completed to date | `int` |
| `mostPopularDestinationEra` | Most booked destination era | `String` |
| `currentStockPrice` | TMCORP stock price on NYSE | `double` |

---

## Response Schema

```json
{
  "role": "assistant",
  "content": "string — the assistant's reply",
  "toolCalls": [
    {
      "name": "string — tool method name",
      "description": "string — what the tool does",
      "input": { "key": "value" },
      "output": "string | number | boolean"
    }
  ]
}
```

> `toolCalls` is omitted when no tools were invoked.

---

## DeepEval Integration

Use `chatbot.py` and `chatbotDemo.py` in `technical-documents/deepeval/` to build DeepEval test cases from the API responses.

```python
import chatbot
from deepeval.test_case import LLMTestCase, ToolCall
from deepeval.metrics import ToolCorrectnessMetric

# Simplest — single message
test_case = chatbot.test_case("How many people are time traveling right now?")

# With expected output and expected tools
test_case = chatbot.test_case(
    "What is the current market price?",
    expected_output="The current ticket price is $250,000.",
    expected_tools=[
        ToolCall(
            name="currentMarketPrice",
            description="Returns the current market price for a standard time travel ticket in USD.",
            input_parameters={},
            output=250000.0
        )
    ]
)

# Multi-turn conversation
test_case = chatbot.test_case_from_conversation([
    {"role": "user",      "content": "What is the market price?"},
    {"role": "assistant", "content": "The price is $250,000."},
    {"role": "user",      "content": "And what is the net profit?"},
])
```

### Run the interactive demo

```bash
cd technical-documents/deepeval
python chatbotDemo.py
```

---

## Configuration

Edit `src/main/resources/application.properties`:

```properties
ollama.base-url=http://localhost:11434
ollama.chat-model=qwen3.5      # swap to any model pulled in Ollama
ollama.temperature=0.1
ollama.timeout-seconds=120

rag.chunk-size=500               # PDF chunk size in characters
rag.chunk-overlap=50             # overlap between chunks
```
