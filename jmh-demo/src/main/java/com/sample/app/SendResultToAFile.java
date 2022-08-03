package com.sample.app;

import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class SendResultToAFile {
	
	@Benchmark
	public void test1() throws InterruptedException {
		TimeUnit.MILLISECONDS.sleep(300);
	}
	
	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(OptionsBuilderDemo.class.getSimpleName())
				.forks(1)
				.measurementIterations(4)
				.warmupIterations(3)
				.resultFormat(ResultFormatType.JSON)
				.result("/Users/Shared/result.json")
				.build();

		new Runner(opt).run();
	}
}