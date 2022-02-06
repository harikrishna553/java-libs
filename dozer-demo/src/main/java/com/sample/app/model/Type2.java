package com.sample.app.model;

public class Type2 {
	private int a;
	private int b;

	public Type2() {
	}

	public Type2(int a, int b) {
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
		return "Type2 [a=" + a + ", b=" + b + "]";
	}

}
