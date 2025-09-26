package com.sample.app.virtual.threads;

import java.util.ArrayList;
import java.util.List;

public class MaxVirtualThreadsCheck {

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.out.println("Usage: java -Xmx1G MaxVirtualThreadsCheck <noOfThreadsToTest>");
            return;
        }

        int noOfThreadsToTest;
        try {
            noOfThreadsToTest = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Please provide a valid integer for number of threads.");
            return;
        }

        System.out.println("Starting virtual thread test with " + noOfThreadsToTest + " threads...");

        // Store references to all virtual threads
        List<Thread> threads = new ArrayList<>();

        for (int i = 1; i <= noOfThreadsToTest; i++) {
            final int threadNumber = i;

            try {
                Thread t = Thread.ofVirtual().start(() -> {
                    System.out.println("Virtual Thread " + threadNumber + " started (ID: "
                            + Thread.currentThread().threadId() + ")");
                    try {
                        Thread.sleep(10_000); // simulate some blocking work
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                        System.out.println("Virtual Thread " + threadNumber + " interrupted");
                    }
                    System.out.println("Virtual Thread " + threadNumber + " finished");
                });

                threads.add(t); // keep reference

            } catch (OutOfMemoryError | Exception e) {
                System.out.println("Failed to create virtual thread " + threadNumber + ": " + e.getMessage());
                break;
            }
        }

        System.out.println("Waiting for all virtual threads to finish...");

        // Wait for all threads to finish
        for (Thread t : threads) {
            t.join();
        }

        System.out.println("All virtual threads finished.");
    }
}
