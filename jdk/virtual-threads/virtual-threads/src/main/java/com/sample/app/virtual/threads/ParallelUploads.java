package com.sample.app.virtual.threads;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ParallelUploads {

    public static String uploadFileA() throws InterruptedException {
        Thread.sleep(3000);
        return "File A uploaded";
    }

    public static String uploadFileB() throws InterruptedException {
        Thread.sleep(4000);
        return "File B uploaded";
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            Future<String> futureA = executor.submit(() -> uploadFileA());
            Future<String> futureB = executor.submit(() -> uploadFileB());

            String resultA = futureA.get();
            String resultB = futureB.get();

            long end = System.currentTimeMillis();

            System.out.println(resultA + ", " + resultB);
            System.out.println("Time taken: " + (end - start) + " ms");
        }
    }
}
