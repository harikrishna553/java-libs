package com.sample.app.recrods;

public record Mul(int... a) implements Operation {

	@Override
	public Long operation() {
		if (a == null || a.length == 0) {
			return null;
		}

		long result = 1;

		for (int ele : a) {
			result *= ele;
		}

		return result;
	}

}
