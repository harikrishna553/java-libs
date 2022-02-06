package com.sample.app.model;

public class Type3 {

	private String a;
	private double b;

	public Type3() {
	}

	public Type3(String a, double b) {
		this.a = a;
		this.b = b;
	}

	public String getA() {
		return a;
	}

	public void setA(String a) {
		this.a = a;
	}

	public double getB() {
		return b;
	}

	public void setB(double b) {
		this.b = b;
	}

	@Override
	public String toString() {
		return "Type3 [a=" + a + ", b=" + b + "]";
	}

}
