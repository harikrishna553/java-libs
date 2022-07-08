package com.sample.app;

import java.time.Duration;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

import io.github.resilience4j.ratelimiter.*;

public class HelloWorld {

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

		RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom().limitForPeriod(2).timeoutDuration(Duration.ofSeconds(4))
				.limitRefreshPeriod(Duration.ofSeconds(10)).build();
		RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);
		RateLimiter rateLimiter = rateLimiterRegistry.rateLimiter("myRateLimiter");
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
