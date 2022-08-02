package com.sample.app;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

public class JMHRunnerDemo {
	private static final String INPUT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final int RAND_STR_LENGTH = 16;

	@Benchmark
	public String randString() throws InterruptedException {

		// Some intentional delay
		TimeUnit.MILLISECONDS.sleep(10);
		final StringBuilder sb = new StringBuilder(RAND_STR_LENGTH);

		for (int i = 0; i < RAND_STR_LENGTH; i++)
			sb.append(INPUT_CHARS.charAt(SECURE_RANDOM.nextInt(INPUT_CHARS.length())));

		return sb.toString();
	}

	public static void main(String[] args) throws RunnerException {
		Options opt = new OptionsBuilder()
				.include(JMHRunnerDemo.class.getSimpleName())
				.forks(1)
				.measurementIterations(4)
				.warmupIterations(3)
				.build();

		new Runner(opt).run();
	}

}