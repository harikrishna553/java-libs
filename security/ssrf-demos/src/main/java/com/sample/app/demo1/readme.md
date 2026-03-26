# 🛡️ SSRF Demo Application (Java)

This project demonstrates a **real-world Server-Side Request Forgery (SSRF)** vulnerability using a minimal Java setup based on `ServerSocket`.

It shows how a seemingly harmless API that fetches external URLs can be abused to **access internal services and sensitive data**.

---

# 📌 What This Application Does

This demo consists of **two servers**:

## 1. 🔒 Internal Service (Private)
- Runs on **port 9090**
- Simulates a **sensitive internal API**
- Returns confidential data (e.g., database password)

👉 This service is **not meant to be publicly accessible**

---

## 2. 🌐 Vulnerable Server (Public API)
- Runs on **port 8080**
- Accepts a URL from the user
- Fetches the content using Java `HttpClient`

```java
public String fetch(String url) throws Exception {
    return HttpClient.newHttpClient()
        .send(HttpRequest.newBuilder(URI.create(url)).GET().build(),
              HttpResponse.BodyHandlers.ofString())
        .body();
}
```

👉 This is the **SSRF entry point**

---

# 🧠 How SSRF Happens in This App

```
Attacker → Vulnerable Server (8080) → Internal Service (9090)
```

1. The attacker sends a request with a URL
2. The vulnerable server fetches that URL
3. The request is executed from the server’s network
4. Internal services become accessible

---

# 🚨 Why This Is Dangerous

Even though the attacker cannot directly access:

```
http://localhost:9090
```

They can exploit the vulnerable server:

```bash
curl "http://localhost:8080/fetch?url=http://localhost:9090"
```

👉 The server fetches the internal service and returns:

```
SECRET: Database password = super-secret
```

---

# 🏗️ Project Structure

```
.
├── InternalService.java
├── VulnerableServer.java
└── README.md
```

---

# ⚙️ Prerequisites

- Java 11 or higher
- Basic understanding of HTTP

---

# 🚀 How to Run

## Step 1: Compile the code

```bash
javac InternalService.java VulnerableServer.java
```

## Step 2: Start Internal Service

```bash
java InternalService
```

## Step 3: Start Vulnerable Server

```bash
java VulnerableServer
```

---

# 🧪 Testing

## Normal request

```bash
curl "http://localhost:8080/fetch?url=https://example.com"
```

## SSRF attack

```bash
curl "http://localhost:8080/fetch?url=http://localhost:9090"
```

---

# ⚡ Final Thought

SSRF is about losing control over where your server can connect.
