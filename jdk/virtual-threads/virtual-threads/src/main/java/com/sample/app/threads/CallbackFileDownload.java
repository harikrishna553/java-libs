package com.sample.app.threads;

import java.util.concurrent.CompletableFuture;

public class CallbackFileDownload {
    public static void main(String[] args) {
        log("Starting async tasks...");

        // Simulate remote file download
        CompletableFuture<String> remoteFileFuture = CompletableFuture.supplyAsync(() -> {
            log("Downloading remote file...");
            sleep(500); // simulate delay
            return "remote-data";
        });

        // Simulate local file read
        CompletableFuture<String> localFileFuture = CompletableFuture.supplyAsync(() -> {
            log("Reading local file...");
            sleep(300); // simulate delay
            return "local-data";
        });

        // Combine both results (callback style)
        remoteFileFuture.thenCombine(localFileFuture, (remote, local) -> {
            log("Combining results...");
            return remote + " + " + local;
        }).thenAccept(result -> {
            log("Sending response...");
            sleep(200); // simulate delay
            System.out.println("[Result Ignored]");
        }).exceptionally(ex -> {
            log("Error occurred: " + ex.getMessage());
            return null;
        });

        // Keep main thread alive until tasks finish
        sleep(2000);
    }

    // Utility method to log with thread info
    static void log(String message) {
        System.out.printf("[%s] %s%n", Thread.currentThread().getName(), message);
    }

    // Helper to simulate delay
    static void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}
