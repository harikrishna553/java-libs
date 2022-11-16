package com.sample.app.components;

import com.sample.app.advices.LogAdvice;

import jakarta.inject.Singleton;

@Singleton
public class ArithmeticUtil {

	@LogAdvice
	public Integer add(int a, int b) {
		return a + b;
	}
}
