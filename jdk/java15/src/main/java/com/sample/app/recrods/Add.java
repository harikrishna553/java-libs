package com.sample.app.recrods;

public record Add(int... a) implements Operation {

	@Override
	public Long operation() {
		if(a == null || a.length == 0) {
			return null;
		}
		
		long sum = 0;
		
		for(int ele : a) {
			sum += ele;
		}
		
		return sum;
	}

}
