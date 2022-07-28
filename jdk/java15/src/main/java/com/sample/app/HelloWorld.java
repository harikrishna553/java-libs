package com.sample.app;

public class HelloWorld {

	public static void main(String[] args) {
		String javaVersion = System.getProperty("java.version");

		System.out.println("Java version : %s".formatted(javaVersion));
	}

}
