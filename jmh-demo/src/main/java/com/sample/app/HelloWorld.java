package com.sample.app;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Warmup;

public class HelloWorld {

	private static final String INPUT_CHARS = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	private static SecureRandom SECURE_RANDOM = new SecureRandom();
	private static final int RAND_STR_LENGTH = 16;

	@Benchmark
	@Fork(value = 2)
	@Warmup(iterations = 2)
	@Measurement(iterations = 8)
	@BenchmarkMode(Mode.AverageTime)
	public String randString() throws InterruptedException {
		// Some intentional delay
		TimeUnit.MILLISECONDS.sleep(10);
		final StringBuilder sb = new StringBuilder(RAND_STR_LENGTH);

		for (int i = 0; i < RAND_STR_LENGTH; i++)
			sb.append(INPUT_CHARS.charAt(SECURE_RANDOM.nextInt(INPUT_CHARS.length())));

		return sb.toString();
	}

	public static void main(String[] args) throws IOException {
		org.openjdk.jmh.Main.main(args);
	}

}
