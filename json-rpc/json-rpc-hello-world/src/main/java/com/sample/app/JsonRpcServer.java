package com.sample.app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * A simple JSON-RPC 2.0 Server implementation
 * Uses built-in SimpleJsonParser (no external dependencies for RPC protocol)
 * Provides arithmetic methods: add, subtract, multiply, divide
 */
public class JsonRpcServer {
    private static final int PORT = 8888;
    private ServerSocket serverSocket;

    public static void main(String[] args) throws IOException {
        JsonRpcServer server = new JsonRpcServer();
        server.start();
    }

    public void start() throws IOException {
        serverSocket = new ServerSocket(PORT);
        System.out.println("🚀 JSON-RPC Server started on port " + PORT);
        System.out.println("📝 Available methods: add, subtract, multiply, divide");
        System.out.println("⏳ Waiting for client connections...\n");

        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.println("✓ New client connected from: " + clientSocket.getInetAddress());
            new Thread(new ClientHandler(clientSocket)).start();
        }
    }

    /**
     * Handles individual client connections
     */
    private static class ClientHandler implements Runnable {
        private Socket socket;
        private PrintWriter writer;
        private BufferedReader reader;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
                );
                writer = new PrintWriter(
                    new OutputStreamWriter(socket.getOutputStream()), true
                );

                String line;
                while ((line = reader.readLine()) != null && !line.isEmpty()) {
                    System.out.println("\n[SERVER] Received request: " + line);

                    try {
                        // Parse JSON-RPC request
                        JsonValue requestValue = SimpleJsonParser.parse(line);

                        if (requestValue.getType() != JsonType.OBJECT) {
                            throw new RuntimeException("Request must be a JSON object");
                        }

                        // Process request and generate response
                        JsonValue response = handleRequest(requestValue);

                        // Send response
                        String responseStr = response.toJsonString();
                        System.out.println("[SERVER] Sending response: " + responseStr);
                        writer.println(responseStr);
                        writer.flush();
                    } catch (Exception e) {
                        System.err.println("[SERVER] Error processing request: " + e.getMessage());
                        String errorResponse = buildErrorResponse(-32700, "Parse error: " + e.getMessage());
                        writer.println(errorResponse);
                        writer.flush();
                    }
                }

                socket.close();
                System.out.println("[SERVER] Client disconnected\n");
            } catch (IOException e) {
                System.err.println("[SERVER] Error handling client: " + e.getMessage());
            }
        }

        /**
         * Handles JSON-RPC method calls
         */
        private JsonValue handleRequest(JsonValue requestValue) throws Exception {
            JsonValue jsonResponse = new SimpleJsonBuilder()
                    .add("jsonrpc", "2.0")
                    .build();

            try {
                var requestObj = requestValue.asObject();

                // Extract request fields
                String method = "";
                double a = 0, b = 0;
                JsonValue id = null;

                if (requestObj.containsKey("method") && !requestObj.get("method").isNull()) {
                    method = requestObj.get("method").asString();
                }

                if (requestObj.containsKey("id")) {
                    id = requestObj.get("id");
                }

                // Validate method
                if (method == null || method.isEmpty()) {
                    return buildErrorResponseValue(-32600, "Invalid Request: missing method", id);
                }

                // Extract parameters
                if (requestObj.containsKey("params") && requestObj.get("params").getType() == JsonType.ARRAY) {
                    var params = requestObj.get("params").asArray();
                    if (params.size() >= 2) {
                        a = params.get(0).asDouble();
                        b = params.get(1).asDouble();
                    }
                } else {
                    return buildErrorResponseValue(-32602, "Invalid params: expected array with 2 numbers", id);
                }

                double result = 0;
                boolean found = true;

                // Dispatch to appropriate method
                if ("add".equals(method)) {
                    result = add(a, b);
                } else if ("subtract".equals(method)) {
                    result = subtract(a, b);
                } else if ("multiply".equals(method)) {
                    result = multiply(a, b);
                } else if ("divide".equals(method)) {
                    result = divide(a, b);
                } else {
                    found = false;
                    return buildErrorResponseValue(-32601, "Method not found: " + method, id);
                }

                if (found) {
                    return buildSuccessResponseValue(result, id);
                }

                return buildErrorResponseValue(-32000, "Unknown error", id);

            } catch (IllegalArgumentException e) {
                JsonValue id = requestValue.asObject().get("id");
                return buildErrorResponseValue(-32000, e.getMessage(), id);
            }
        }

        private JsonValue buildSuccessResponseValue(double result, JsonValue id) {
            var response = new SimpleJsonBuilder();
            response.add("jsonrpc", "2.0");
            response.add("result", result);
            if (id != null && !id.isNull()) {
                response.addValue("id", id);
            } else {
                response.addNull("id");
            }
            return response.build();
        }

        private JsonValue buildErrorResponseValue(int code, String message, JsonValue id) {
            var response = new SimpleJsonBuilder();
            response.add("jsonrpc", "2.0");

            var error = new SimpleJsonBuilder();
            error.add("code", code);
            error.add("message", message);
            response.addValue("error", error.build());

            if (id != null && !id.isNull()) {
                response.addValue("id", id);
            } else {
                response.addNull("id");
            }
            return response.build();
        }

        private String buildErrorResponse(int code, String message) {
            return "{\"jsonrpc\":\"2.0\",\"id\":null,\"error\":{\"code\":" + code +
                   ",\"message\":\"" + escapeJson(message) + "\"}}";
        }

        private String escapeJson(String s) {
            return s.replace("\\", "\\\\")
                    .replace("\"", "\\\"")
                    .replace("\n", "\\n");
        }

        // Arithmetic methods
        private double add(double a, double b) {
            System.out.println("  ✓ Executing: add(" + a + ", " + b + ")");
            return a + b;
        }

        private double subtract(double a, double b) {
            System.out.println("  ✓ Executing: subtract(" + a + ", " + b + ")");
            return a - b;
        }

        private double multiply(double a, double b) {
            System.out.println("  ✓ Executing: multiply(" + a + ", " + b + ")");
            return a * b;
        }

        private double divide(double a, double b) {
            System.out.println("  ✓ Executing: divide(" + a + ", " + b + ")");
            if (b == 0) {
                throw new IllegalArgumentException("Division by zero");
            }
            return a / b;
        }
    }
}
