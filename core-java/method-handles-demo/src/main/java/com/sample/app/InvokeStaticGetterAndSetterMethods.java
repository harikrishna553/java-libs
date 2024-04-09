package com.sample.app;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class InvokeStaticGetterAndSetterMethods {

	private static class SampleObject {
		private static int value = 123;

		public static int getValue() {
			return value;
		}

		public static void setValue(int val) {
			SampleObject.value = val;
		}

		public static void print() {
			System.out.println("SampleObject [value=" + value + "]");
		}

	}

	public static void main(String[] args) throws Throwable {

		SampleObject.print();
		// MethodHandles lookup
		MethodHandles.Lookup lookup = MethodHandles.lookup();

		// Lookup the getter method
		MethodHandle getterMethodHandle = lookup.findStaticGetter(SampleObject.class, "value", int.class);
		int value = (int) getterMethodHandle.invoke();
		System.out.println("Value obtained through getter: " + value);

		System.out.println("\nSet the value to 12345");
		MethodHandle setterMethodHandle = lookup.findStaticSetter(SampleObject.class, "value", int.class);
		setterMethodHandle.invoke(12345);
		SampleObject.print();

	}

}
