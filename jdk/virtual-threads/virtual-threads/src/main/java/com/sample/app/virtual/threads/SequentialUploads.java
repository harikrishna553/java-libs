package com.sample.app.virtual.threads;

public class SequentialUploads {

    public static String uploadFileA() throws InterruptedException {
        Thread.sleep(3000); // simulating file upload
        return "File A uploaded";
    }

    public static String uploadFileB() throws InterruptedException {
        Thread.sleep(4000); // simulating file upload
        return "File B uploaded";
    }

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();

        String resultA = uploadFileA();
        String resultB = uploadFileB();

        long end = System.currentTimeMillis();

        System.out.println(resultA + ", " + resultB);
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
