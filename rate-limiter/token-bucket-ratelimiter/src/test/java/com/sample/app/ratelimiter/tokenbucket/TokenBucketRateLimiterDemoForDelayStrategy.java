package com.sample.app.ratelimiter.tokenbucket;

import java.util.Date;
import java.util.UUID;

import com.sample.app.ratelmiter.util.TimeUtil;

public class TokenBucketRateLimiterDemoForDelayStrategy {

	public static void main(String[] args) {
		TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter();

		String resourceIdentifier = UUID.randomUUID().toString();
		TokenBucket tokenBucket = TokenBucket.delayRequestTokenBucket(5, 2);

		rateLimiter.mapBucketToAResource(resourceIdentifier, tokenBucket);

		for (int i = 0; i < 100; i++) {
			boolean canIConsume = tokenBucket.tryConsume();

			if (canIConsume) {
				System.out.println("Token is assigned " + new Date());
			} else {
				TimeUtil.sleepNMilliSeconds(500);
				System.out.println("Tokens are not available");
			}

		}
	}

}
