package com.sample.app;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

public class RateLimiterEventListenerDemo {
	public static void main(String[] args) {

		Function<String, String> toUpper = new Function<String, String>() {

			@Override
			public String apply(String t) {
				try {
					TimeUnit.MILLISECONDS.sleep(250);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return t.toUpperCase();
			}

		};

		RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom().limitForPeriod(2)
				.timeoutDuration(Duration.ofSeconds(4)).limitRefreshPeriod(Duration.ofSeconds(10)).build();
		RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
		RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("myRateLimiter");

		rateLimiter.getEventPublisher().onSuccess(rateLimiterOnSuccessEvent -> {
			System.out.println("\n----------------------------------------------------------------------");
			System.out.print("Success event received");
			System.out.println("Rate limiter name : " + rateLimiterOnSuccessEvent.getRateLimiterName());
			System.out.println("Number of Permits : " + rateLimiterOnSuccessEvent.getNumberOfPermits());
			System.out.println("Event creation time : " + rateLimiterOnSuccessEvent.getCreationTime());
			System.out.println("Event Type : " + rateLimiterOnSuccessEvent.getEventType().name());
			System.out.println("----------------------------------------------------------------------");

		}).onFailure(rateLimiterOnFailureEvent -> {
			System.out.println("\n----------------------------------------------------------------------");
			System.out.print("Failure event received");
			System.out.println("Rate limiter name : " + rateLimiterOnFailureEvent.getRateLimiterName());
			System.out.println("Number of Permits : " + rateLimiterOnFailureEvent.getNumberOfPermits());
			System.out.println("Event creation time : " + rateLimiterOnFailureEvent.getCreationTime());
			System.out.println("Event Type : " + rateLimiterOnFailureEvent.getEventType().name());
			System.out.println("----------------------------------------------------------------------");
		});

		Function<String, String> decorated = RateLimiter.decorateFunction(rateLimiter, toUpper);

		while (true) {
			try {
				String str = decorated.apply("hello");
				System.out.println(new Date() + "\t" + str);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
