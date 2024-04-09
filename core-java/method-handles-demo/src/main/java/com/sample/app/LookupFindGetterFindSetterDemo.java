package com.sample.app;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;

public class LookupFindGetterFindSetterDemo {

	private static class SampleObject {
		private int value;

		public SampleObject(int value) {
			this.value = value;
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return "SampleObject [value=" + value + "]";
		}

	}

	public static void main(String[] args) throws Throwable {
		// Sample object with a getter method
		SampleObject obj = new SampleObject(42);
		System.out.println("obj : " + obj);

		// MethodHandles lookup
		MethodHandles.Lookup lookup = MethodHandles.lookup();

		// Lookup the getter method
		MethodHandle getterMethodHandle = lookup.findGetter(SampleObject.class, "value", int.class);
		int value = (int) getterMethodHandle.invoke(obj);
		System.out.println("Value obtained through getter: " + value);

		System.out.println("\nSet the value to 12345");
		MethodHandle setterMethodHandle = lookup.findSetter(SampleObject.class, "value", int.class);
		setterMethodHandle.invoke(obj, 12345);
		System.out.println("obj : " + obj);

	}

}