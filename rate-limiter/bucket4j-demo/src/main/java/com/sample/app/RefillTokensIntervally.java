package com.sample.app;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;

public class RefillTokensIntervally {

	private static Bucket bucket;

	private static void getResource() {

		final long noOfTokensLeft = bucket.getAvailableTokens();

		if (noOfTokensLeft == 0L) {
			System.err.println("Number of tokens left : " + bucket.getAvailableTokens());
		} else {
			System.out.println("Number of tokens left : " + bucket.getAvailableTokens());
		}

		if (bucket.tryConsume(1)) {
			System.out.println("Resource accessed....\n");
			return;
		}

		System.err.println("Too Many Requests, all the tokens consumed\n");
	}

	public static void main(String[] args) throws InterruptedException {
		Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofSeconds(10)));
		bucket = Bucket.builder().addLimit(limit).build();

		while (true) {
			TimeUnit.SECONDS.sleep(1);
			getResource();
		}
	}

}
