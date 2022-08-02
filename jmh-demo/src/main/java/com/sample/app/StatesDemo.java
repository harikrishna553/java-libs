package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class StatesDemo {
	
	@State(Scope.Benchmark)
	public static class BenchmarkState {
	}

	@State(Scope.Thread)
	public static class ThreadState {
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void benchmarkState(BenchmarkState benchmarkState) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		String threadName = Thread.currentThread().getName();
		String threadGroupName = Thread.currentThread().getThreadGroup().getName();

		System.out.println("threadName : %s, threadGroupName %s, benchmarkState : %s".formatted(threadName,
				threadGroupName, benchmarkState));
	}

	@Benchmark
	@BenchmarkMode(Mode.AverageTime)
	public void threadState(ThreadState threadState) throws InterruptedException {
		TimeUnit.SECONDS.sleep(1);
		String threadName = Thread.currentThread().getName();
		String threadGroupName = Thread.currentThread().getThreadGroup().getName();

		System.out.println("threadName : %s, threadGroupName %s, threadState : %s".formatted(threadName,
				threadGroupName, threadState));
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(StatesDemo.class.getSimpleName())
				.threads(3)
				.threadGroups(2)
				.forks(1)
				.measurementIterations(1)
				.warmupIterations(1)
				.build();
		

		Runner runner = new Runner(opt);
		runner.run();
		

	}
}
