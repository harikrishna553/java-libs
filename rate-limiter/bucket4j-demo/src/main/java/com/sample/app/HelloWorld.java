package com.sample.app;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

public class HelloWorld {

	private static Bucket bucket;

	private static void getResource() {

		if (bucket.tryConsume(1)) {
			System.out.println("Resource accessed....");
			return;
		}

		System.err.println("Too Many Requests, all the tokens consumed");
	}

	public static void main(String[] args) throws InterruptedException {
		Bandwidth limit = Bandwidth.classic(5, Refill.greedy(10, Duration.ofSeconds(30)));
		bucket = Bucket.builder().addLimit(limit).build();

		while (true) {
			TimeUnit.SECONDS.sleep(1);
			getResource();
		}
	}

}
