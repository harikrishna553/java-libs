package com.sample.app;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.profiler.Profiler;
import org.slf4j.profiler.TimeInstrument;

public class ProfilingDemo {

	private static final Logger LOGGER = LoggerFactory.getLogger(ParameterizedLoggingDemo.class);

	private Map<Integer, Integer> internalCache = new HashMap<>();

	public int fibWithDynamicProgramming(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("Fibonaaci can't be calucated for -ve integers");
		}

		return fibWithDynamicProgramming1(n);

	}

	private int fibWithDynamicProgramming1(int n) {
		if (n == 0) {
			return 0;
		}

		if (n == 1) {
			return 1;
		}

		Integer value = internalCache.get(n);

		if (value != null) {
			return value;
		}

		int result = fibWithDynamicProgramming1(n - 1) + fibWithDynamicProgramming1(n - 2);
		internalCache.put(n, result);

		return result;
	}

	public int fibRecursive(int n) {
		if (n == 0) {
			return 0;
		}

		if (n == 1) {
			return 1;
		}

		return fibRecursive(n - 1) + fibRecursive(n - 2);
	}

	public static void main(String args[]) {
		ProfilingDemo demo = new ProfilingDemo();

		Profiler profiler = new Profiler("Fibonacci test");
		profiler.setLogger(LOGGER);

		profiler.start("with recursion");
		demo.fibRecursive(40);

		profiler.start("with Dynamic Programming");
		demo.fibWithDynamicProgramming(40);

		TimeInstrument timeInstrument = profiler.stop();

		timeInstrument.print();

	}

}
