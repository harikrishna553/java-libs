package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Thread)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class DeadcodeEliminationDemo {

	private double fib(int n) {
		if (n <= 1) {
			return n;
		}
		
		int fib = 1;
		int prevFib = 1;

		for (int i = 2; i < n; i++) {
			int temp = fib;
			fib += prevFib;
			prevFib = temp;
		}
		return fib;

	}

	@Benchmark
	public void baseline() {
		// do nothing
	}

	@Benchmark
	public void measureWrong() {
		fib(20);
	}

	@Benchmark
	public double measureRight() {
		// Here result is returned to the calling function.
		return fib(20);
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder().include(DeadcodeEliminationDemo.class.getSimpleName()).forks(1)
				.measurementIterations(2).warmupIterations(2).build();

		new Runner(opt).run();
	}

}
