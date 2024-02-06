package com.sample.app.ratelimiter.tokenbucket;

import java.util.UUID;

import com.sample.app.ratelmiter.util.TimeUtil;

public class TokenBucketRateLimiterDemoForRejectStrategy {

	public static void main(String[] args) {
		TokenBucketRateLimiter rateLimiter = new TokenBucketRateLimiter();

		String resourceIdentifier = UUID.randomUUID().toString();
		TokenBucket tokenBucket = TokenBucket.rejectRequestTokenBucket(5, 5);

		rateLimiter.mapBucketToAResource(resourceIdentifier, tokenBucket);

		for (int i = 0; i < 100; i++) {
			boolean canIConsume = tokenBucket.tryConsume();

			if (canIConsume) {
				System.out.println("Token is assigned");
			} else {
				TimeUtil.sleepNMilliSeconds(500);
				System.out.println("Tokens are not available");
			}

		}
	}

}
