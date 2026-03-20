package com.sample.app;

import java.io.*;
import java.net.Socket;

/**
 * A simple JSON-RPC 2.0 Client
 * Sends method calls to the server and receives responses
 * Uses SimpleJsonParser for JSON handling
 */
public class JsonRpcClient {
    private static final String HOST = "localhost";
    private static final int PORT = 8888;
    private static final int CONNECT_TIMEOUT_MS = 5000;

    private Socket socket;
    private PrintWriter writer;
    private BufferedReader reader;
    private int requestId = 0;

    public static void main(String[] args) {
        JsonRpcClient client = new JsonRpcClient();
        try {
            client.connect();
            client.demonstrateRpc();
        } catch (IOException e) {
            System.err.println("❌ Error: " + e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                client.disconnect();
            } catch (IOException e) {
                System.err.println("Error disconnecting: " + e.getMessage());
            }
        }
    }

    /**
     * Connects to the JSON-RPC server
     */
    public void connect() throws IOException {
        System.out.println("🔗 Attempting to connect to " + HOST + ":" + PORT + "...");
        try {
            socket = new Socket(HOST, PORT);
            writer = new PrintWriter(
                new OutputStreamWriter(socket.getOutputStream()), true
            );
            reader = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            System.out.println("✓ Connected to JSON-RPC Server at " + HOST + ":" + PORT + "\n");
        } catch (IOException e) {
            System.err.println("❌ Connection failed. Make sure the server is running.");
            throw e;
        }
    }

    /**
     * Demonstrates various RPC method calls
     */
    public void demonstrateRpc() throws IOException {
        System.out.println("=== JSON-RPC Method Calls ===\n");

        // Test add
        callMethod("add", new double[]{10, 5});

        // Test subtract
        callMethod("subtract", new double[]{20, 7});

        // Test multiply
        callMethod("multiply", new double[]{6, 4});

        // Test divide
        callMethod("divide", new double[]{15, 3});

        // Test another addition
        callMethod("add", new double[]{100, 25});

        // Test division by zero (error case)
        callMethod("divide", new double[]{10, 0});

        System.out.println("\n=== All calls completed ===");
    }

    /**
     * Calls a JSON-RPC method and prints the result
     */
    private void callMethod(String methodName, double[] params) throws IOException {
        requestId++;

        // Build JSON-RPC request manually or using a builder
        String requestStr = buildJsonRpcRequest(methodName, params, requestId);

        System.out.println("[CLIENT] Request [" + requestId + "]: " + requestStr);
        writer.println(requestStr);
        writer.flush();

        // Receive response
        String responseStr = reader.readLine();
        System.out.println("[CLIENT] Response[" + requestId + "]: " + responseStr);

        // Parse and display result
        try {
            JsonValue response = SimpleJsonParser.parse(responseStr);

            if (response.getType() != JsonType.OBJECT) {
                System.out.println("  ❌ Invalid response format");
                return;
            }

            var responseObj = response.asObject();

            if (responseObj.containsKey("error") && !responseObj.get("error").isNull()) {
                JsonValue error = responseObj.get("error");
                if (error.getType() == JsonType.OBJECT) {
                    var errorObj = error.asObject();
                    String message = errorObj.containsKey("message") ?
                            errorObj.get("message").asString() : "Unknown error";
                    System.out.println("  ❌ Error: " + message);
                } else {
                    System.out.println("  ❌ Error: " + error.asString());
                }
            } else if (responseObj.containsKey("result") && !responseObj.get("result").isNull()) {
                JsonValue result = responseObj.get("result");
                System.out.println("  ✓ Result: " + result.asDouble());
            } else {
                System.out.println("  ❌ Invalid response: no result or error");
            }
        } catch (Exception e) {
            System.out.println("  ❌ Error parsing response: " + e.getMessage());
        }

        System.out.println();
    }

    /**
     * Builds a JSON-RPC request string manually
     */
    private String buildJsonRpcRequest(String methodName, double[] params, int id) {
        StringBuilder sb = new StringBuilder();
        sb.append("{\"jsonrpc\":\"2.0\",\"method\":\"").append(methodName).append("\",");
        sb.append("\"params\":[");
        for (int i = 0; i < params.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(params[i]);
        }
        sb.append("],\"id\":").append(id).append("}");
        return sb.toString();
    }

    /**
     * Disconnects from the server
     */
    public void disconnect() throws IOException {
        if (socket != null && !socket.isClosed()) {
            socket.close();
            System.out.println("🔌 Disconnected from server");
        }
    }
}
