package com.sample.app.threads;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

public class VirtualThreadsFileDownload {
	public static void main(String[] args) throws Exception {
		System.out.println("Starting download with virtual threads...");

		try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
			// Run download task in a virtual thread
			Future<String> remoteFileFuture = executor.submit(() -> {
				logThread("Downloading remote file");
				return downloadFileBlocking("https://raw.githubusercontent.com/pandas-dev/pandas/e503c13e5de42ac2fc675e564b0958a14221b14a/doc/redirects.csv");
			});

			// Run read file task in another virtual thread
			Future<String> localFileFuture = executor.submit(() -> {
				logThread("Reading local file");
				return readFileBlocking("localfile.txt");
			});

			// Combine results in another virtual thread
			Future<Void> responseFuture = executor.submit(() -> {
				String remoteData = remoteFileFuture.get();
				String localData = localFileFuture.get();
				logThread("Sending response");
				sendResponse(remoteData, localData);
				return null;
			});

			responseFuture.get(); // wait for everything
		}
	}

	// Downloads file content (blocking)
	static String downloadFileBlocking(String url) throws IOException, InterruptedException {
		HttpClient client = HttpClient.newHttpClient();
		HttpRequest request = HttpRequest.newBuilder().uri(URI.create(url)).build();
		HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
		return response.body();
	}

	// Reads file from resources (blocking)
	static String readFileBlocking(String resourceName) throws IOException {
		var resourceUrl = VirtualThreadsFileDownload.class.getClassLoader().getResource(resourceName);
		if (resourceUrl == null) {
			throw new IOException("Resource not found: " + resourceName);
		}
		Path path = Paths.get(resourceUrl.getPath());
		return Files.lines(path).collect(Collectors.joining("\n"));
	}

	// Simulates sending response
	static void sendResponse(String a, String b) throws InterruptedException {
		Thread.sleep(200); // simulate network delay
		System.out.println("\n=== Final Response ===");
		System.out.println(a.substring(0, Math.min(300, a.length())) + "...\n---\n" + b);
	}

	// Logs thread info (including thread ID)
	static void logThread(String task) {
		Thread t = Thread.currentThread();
		System.out.printf("[%s] Thread: %s (id=%d, virtual=%s)%n", task, t.getName(), t.threadId(), t.isVirtual());
	}
}
