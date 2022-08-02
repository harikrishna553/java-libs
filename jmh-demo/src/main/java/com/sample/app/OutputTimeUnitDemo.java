package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class OutputTimeUnitDemo {
	private void sleepNMilliSeconds(int n) {
		try {
			TimeUnit.MILLISECONDS.sleep(n);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.SECONDS)
	public void measureInSeconds() {
		sleepNMilliSeconds(500);
	}
	
	@Benchmark
	@BenchmarkMode(Mode.Throughput)
	@OutputTimeUnit(TimeUnit.MINUTES)
	public void measureInMinutes() {
		sleepNMilliSeconds(500);
	}
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(OutputTimeUnitDemo.class.getSimpleName())
				.forks(1)
				.measurementIterations(4)
				.warmupIterations(3)
				.build();

		new Runner(opt).run();
	}
	
}