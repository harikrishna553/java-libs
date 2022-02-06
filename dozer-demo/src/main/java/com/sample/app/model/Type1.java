package com.sample.app.model;

public class Type1 {
	private int a;
	private int b;

	public Type1() {
	}

	public Type1(int a, int b) {
		this.a = a;
		this.b = b;
	}

	public int getA() {
		return a;
	}

	public void setA(int a) {
		this.a = a;
	}

	public int getB() {
		return b;
	}

	public void setB(int b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Type1 [a=" + a + ", b=" + b + "]";
	}

}
