package com.sample.app.virtual.threads.creation;

public class VirtualThreadExample1 {
    public static void main(String[] args) throws InterruptedException {
        // Create and start a virtual thread
        Thread t = Thread.ofVirtual().start(() -> {
            System.out.println("Hello from a virtual thread!");
        });

        // Wait until the virtual thread finishes its work
        t.join();
    }
}
