package com.sample.app;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class InvokeExactDemo {

	public static void main(String[] args) throws Throwable {
        MethodHandles.Lookup lookup = MethodHandles.lookup();
        MethodType methodType = MethodType.methodType(long.class, long.class);
        MethodHandle methodHandle = lookup.findStatic(InvokeExactDemo.class, "square", methodType);

        // Using invoke()
        int result1 = (int) methodHandle.invoke(5); // Flexibility with type conversion
        System.out.println("Result using invoke(): " + result1);

        // Using invokeExact()
        int result2 = (int) methodHandle.invokeExact(5l); // Exact match required
        System.out.println("Result using invokeExact(): " + result2);
    }

    public static long square(long num) {
        return num * num;
    }

}
