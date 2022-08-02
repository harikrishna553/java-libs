package com.sample.app;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class BenchmarkStateDemo {

	@State(Scope.Benchmark)
	public static class BenchmarkState {
		private AtomicLong totalMethodExecutionCount;
		private String methodName = null;

		@Setup(Level.Trial)
		public void doSetup() {
			totalMethodExecutionCount = new AtomicLong(0);
			System.out.println("Do Setup");
		}

		@TearDown(Level.Trial)
		public void doTearDown() {
			System.out.println("Benchmark Method " + methodName + " executed " + totalMethodExecutionCount.longValue() + " times");
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void measureShared1(BenchmarkState state) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(100);
		state.methodName = "measureShared1";
		state.totalMethodExecutionCount.getAndIncrement();
	}
	
	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void measureShared2(BenchmarkState state) throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(200);
		state.methodName = "measureShared2";
		state.totalMethodExecutionCount.getAndIncrement();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(BenchmarkStateDemo.class.getSimpleName())
				.forks(1)
				.measurementIterations(2)
				.warmupIterations(2).build();

		Runner runner = new Runner(opt);
		runner.run();

	}
}
