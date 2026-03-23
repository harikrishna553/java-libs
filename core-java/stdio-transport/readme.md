# Java stdio Transport Demo

## 📌 Overview
This project demonstrates how processes can communicate using **standard input (stdin)** and **standard output (stdout)** without using HTTP, sockets, or networking.

It includes two examples:

1. **Pipe-based communication**
   - ProgramA → ProgramB
2. **Parent-child communication**
   - StdioClient → StdioServer (using ProcessBuilder)

---

## 🧩 Programs Included

### 1. ProgramA.java (Producer)
- Writes messages to stdout
- Acts as a data producer

### 2. ProgramB.java (Consumer)
- Reads from stdin
- Processes input (converts to uppercase)

---

### 3. StdioClient.java (Parent Process)
- Starts StdioServer using ProcessBuilder
- Sends user input to server
- Reads server response

### 4. StdioServer.java (Child Process)
- Reads input from stdin
- Processes it (uppercase)
- Sends response via stdout

---

## 🔌 How It Works

### Pipe-based
ProgramA stdout → ProgramB stdin

```bash
java ProgramA | java ProgramB
```

---

### Parent-child
Client creates server process and connects streams:

```
Client OutputStream  → Server stdin
Client InputStream   ← Server stdout
```

---

## ▶️ How to Run

### Step 1: Compile
```bash
javac ProgramA.java ProgramB.java StdioClient.java StdioServer.java
```

---

### Step 2: Run Pipe Example
```bash
java ProgramA | java ProgramB
```

---

### Step 3: Run Client-Server Example
```bash
java StdioClient
```

Type input and see responses.

---

## 🧠 Key Concepts

- Every process has stdin, stdout, stderr
- OS connects streams
- No networking required
- Communication is stream-based

---

## 🔥 Key Takeaway

stdio transport is:
- Simple
- Fast
- Ideal for local process communication

---

## 🚀 Use Cases

- CLI tools
- AI integrations
- Local services
- Developer tooling

---

## 📄 License
Free to use for learning and experimentation.

