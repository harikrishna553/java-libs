package com.sample.app.threads;

public class StackFrameDemo {

    private static void printStackTrace(String context) {
        System.out.println("\n--- Stack Trace (" + context + ") ---");
        StackTraceElement[] stackTrace = Thread.currentThread().getStackTrace();

        // Skip first two elements (getStackTrace + this method itself)
        for (int i = 2; i < stackTrace.length; i++) {
            StackTraceElement element = stackTrace[i];
            System.out.println("  at " + element.getClassName() + "." + element.getMethodName()
                    + "(" + element.getFileName() + ":" + element.getLineNumber() + ")");
        }
        System.out.println("--- End of Stack Trace ---\n");
    }

    public static void main(String[] args) {
        printStackTrace("Start of main");
        System.out.println("Main thread starts");

        processUserDetails("Alice");

        printStackTrace("After processUserDetails");
        System.out.println("Main thread ends");
        printStackTrace("End of main");
    }

    static void processUserDetails(String name) {
        System.out.println("\nEntered processUserDetails() with name = " + name);
        printStackTrace("Inside processUserDetails - before saveUser");

        // Call another method
        saveUser(name);

        printStackTrace("Inside processUserDetails - after saveUser");
        System.out.println("Exiting processUserDetails()");
    }

    static void saveUser(String name) {
        System.out.println("\nEntered saveUser() with name = " + name);
        printStackTrace("Inside saveUser");

        // Local variable (stored in this method's stack frame)
        boolean success = true;

        if (success) {
            System.out.println("User " + name + " saved successfully!");
        }

        printStackTrace("Exiting saveUser");
        System.out.println("Exiting saveUser()");
    }
}
