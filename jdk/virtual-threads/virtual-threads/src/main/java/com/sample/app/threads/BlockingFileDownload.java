package com.sample.app.threads;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class BlockingFileDownload {
	public static void main(String[] args) throws IOException, InterruptedException {
		System.out.println("Starting download...");

		// Download remote file (blocking)
		String data1 = downloadFileBlocking(
				"https://raw.githubusercontent.com/pandas-dev/pandas/e503c13e5de42ac2fc675e564b0958a14221b14a/doc/redirects.csv");
		System.out.println("Downloaded remote file:\n" + data1);

		// Read local file (blocking)
		String data2 = readFileBlocking("localfile.txt");
		System.out.println("Read local file:\n" + data2);

		// Simulate sending response
		sendResponse(data1, data2);
	}

	// Downloads file content using Java HttpClient (blocking)
	static String downloadFileBlocking(String url) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

		return response.body();
	}

	// Reads file from resources (blocking)
	static String readFileBlocking(String resourceName) throws IOException {
		// Get the file from classpath (works in IDE and JAR)
		var resourceUrl = BlockingFileDownload.class.getClassLoader().getResource(resourceName);
		if (resourceUrl == null) {
			throw new IOException("Resource not found: " + resourceName);
		}
		Path path = Paths.get(resourceUrl.getPath());
		return Files.lines(path).collect(Collectors.joining("\n"));
	}

	// Simulates sending response (blocking)
	static void sendResponse(String a, String b) throws InterruptedException {
		Thread.sleep(200); // simulate network delay
		System.out.println("\n=== Final Response ===");
		System.out.println(a + "\n---\n" + b);
	}
}
