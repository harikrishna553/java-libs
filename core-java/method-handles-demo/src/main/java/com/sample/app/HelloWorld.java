package com.sample.app;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

import com.sample.app.util.ArithmeticUtil;

public class HelloWorld {

	public static void main(String[] args) throws Throwable {
		MethodHandles.Lookup lookup = MethodHandles.lookup();
		MethodType methodType = MethodType.methodType(long.class, int.class, int.class);
		MethodHandle methodHandle = lookup.findStatic(ArithmeticUtil.class, "sum", methodType);

		long result = (long) methodHandle.invoke(10, 20);
		System.out.println("Sum of 10 and 20 is " + result);
	}

}
