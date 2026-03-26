package com.sample.app.demo1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class VulnerableServer {

    public static void main(String[] args) throws Exception {
        ServerSocket serverSocket = new ServerSocket(8080);
        System.out.println("Vulnerable Server running on port 8080...");

        while (true) {
            Socket socket = serverSocket.accept();

            BufferedReader in = new BufferedReader(
                new InputStreamReader(socket.getInputStream())
            );
            BufferedWriter out = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
            );

            // Read HTTP request line
            String requestLine = in.readLine();
            System.out.println("Incoming request: " + requestLine);

            String url = extractUrl(requestLine);

            String responseBody;

            try {
                responseBody = fetch(url);
            } catch (Exception e) {
                responseBody = "Error: " + e.getMessage();
            }

            String httpResponse =
                "HTTP/1.1 200 OK\r\n" +
                "Content-Length: " + responseBody.length() + "\r\n" +
                "\r\n" +
                responseBody;

            out.write(httpResponse);
            out.flush();
            socket.close();
        }
    }

    // 🚨 Vulnerable fetch method
    public static String fetch(String url) throws Exception {
        return HttpClient.newHttpClient()
            .send(HttpRequest.newBuilder(URI.create(url)).GET().build(),
                  HttpResponse.BodyHandlers.ofString())
            .body();
    }

    // Extract URL from query: GET /fetch?url=...
    private static String extractUrl(String requestLine) {
        try {
            String path = requestLine.split(" ")[1];
            String query = path.split("\\?")[1];

            for (String param : query.split("&")) {
                if (param.startsWith("url=")) {
                    return URLDecoder.decode(param.substring(4), "UTF-8");
                }
            }
        } catch (Exception ignored) {}

        return "http://example.com";
    }
}