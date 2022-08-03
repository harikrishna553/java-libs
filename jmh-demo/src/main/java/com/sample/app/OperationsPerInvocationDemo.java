package com.sample.app;

import java.math.BigInteger;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OperationsPerInvocation;
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
public class OperationsPerInvocationDemo {

	private BigInteger b1 = new BigInteger("23433686961963291297886553421867564211323");
	private BigInteger b2 = new BigInteger("9788655342186756421132397886553421867564211323");


	@Benchmark
	@OperationsPerInvocation(10)
	public BigInteger add10TimesWithInvocationCount() {
		for (int i = 0; i < 10; i++) {
			b1.add(b2);
		}

		return b1;
	}
	
	@Benchmark
	public BigInteger add10Times() {
		for (int i = 0; i < 10; i++) {
			b1.add(b2);
		}

		return b1;
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(OperationsPerInvocationDemo.class.getSimpleName())
				.forks(1)
				.measurementIterations(10)
				.warmupIterations(5)
				.build();

		new Runner(opt).run();
	}
}

