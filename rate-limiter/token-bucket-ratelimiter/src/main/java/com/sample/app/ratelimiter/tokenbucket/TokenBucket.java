package com.sample.app.ratelimiter.tokenbucket;

import com.sample.app.ratelmiter.util.NumberUtil;

public class TokenBucket {
	// Fixed state of the token bucket
	private final int bucketSize;
	private final int refillRateInSeconds;
	private final TokenBucketNewRequestStrategy tokenBucketNewRequestStrategy;

	// Dynamic state
	private int tokens;
	private long lastRefillTime;

	private TokenBucket(int bucketSize, int refillRate, TokenBucketNewRequestStrategy tokenBucketNewRequestStrategy) {
		NumberUtil.checkForPositiveNumber(bucketSize, "bucketSize should be positive");
		NumberUtil.checkForPositiveNumber(refillRate, "refillRate should be positive");

		this.bucketSize = bucketSize;
		this.refillRateInSeconds = refillRate;
		this.tokens = bucketSize;
		this.lastRefillTime = System.currentTimeMillis();
		this.tokenBucketNewRequestStrategy = tokenBucketNewRequestStrategy;
	}

	/**
	 * Let's refill the tokens before check for the consumption
	 * 
	 * @return
	 */
	public synchronized boolean tryConsume() {
		refillTokens();

		if (tokenBucketNewRequestStrategy == TokenBucketNewRequestStrategy.DELAY) {
			while (tokens <= 0) {
				try {

					// if waitTime is <=0, means that wait time is already passed
					long waitTime = calculateWaitTime();
					System.out.println("waitTime : " + waitTime);
					if (waitTime > 0) {
						wait(waitTime);
					}

					this.refillTokens();
				} catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					return false; // Interrupted while waiting
				}
			}
		}

		if (tokens > 0) {
			tokens--;
			return true;
		}
		return false;

	}

	private void refillTokens() {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastRefillTime;
		int tokensToAdd = (int) (elapsedTime / (refillRateInSeconds * 1000.0));

		if (tokensToAdd > 0) {
			int newTokens = Math.min(bucketSize, tokens + tokensToAdd);

			// Refill time should be updated only when the tokens are added
			if (newTokens != tokens) {
				lastRefillTime = currentTime;
				tokens = newTokens;
			}
		}

		this.notify();
	}

	private long calculateWaitTime() {
		long currentTime = System.currentTimeMillis();
		long elapsedTime = currentTime - lastRefillTime;
		long millisecondsToWait = ((refillRateInSeconds * 1000) - elapsedTime);
		return millisecondsToWait;
	}

	public static TokenBucket tokenBucket(int bucketSize, int refillRate,
			TokenBucketNewRequestStrategy tokenBucketNewRequestStrategy) {
		return new TokenBucket(bucketSize, refillRate, tokenBucketNewRequestStrategy);
	}

	public static TokenBucket delayRequestTokenBucket(int bucketSize, int refillRate) {
		return tokenBucket(bucketSize, refillRate, TokenBucketNewRequestStrategy.DELAY);
	}

	public static TokenBucket rejectRequestTokenBucket(int bucketSize, int refillRate) {
		return tokenBucket(bucketSize, refillRate, TokenBucketNewRequestStrategy.REJECT);
	}

	public TokenBucket deepClone() {
		return new TokenBucket(this.bucketSize, this.refillRateInSeconds, this.tokenBucketNewRequestStrategy);
	}

	@Override
	public String toString() {
		return "TokenBucket [bucketSize=" + bucketSize + ", refillRateInMilliseconds=" + refillRateInSeconds
				+ ", tokenBucketNewRequestStrategy=" + tokenBucketNewRequestStrategy + ", tokens=" + tokens
				+ ", lastRefillTime=" + lastRefillTime + "]";
	}

}
