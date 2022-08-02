package com.sample.app;

public class DeadCodeEx {
	public static void main(String[] args) {
		final boolean b = true;
		
		if(b) {
			System.out.println("b is set to true");
		}else {
			System.out.println("b is set to false");
		}
	}
}
