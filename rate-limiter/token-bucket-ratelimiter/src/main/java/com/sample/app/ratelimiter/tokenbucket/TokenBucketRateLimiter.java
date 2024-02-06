package com.sample.app.ratelimiter.tokenbucket;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.sample.app.ratelimiter.exceptions.TokenBucketNotFoundException;
import com.sample.app.ratelimiter.interfaces.RateLimiter;

/**
 * Implementation of the Token Bucket rate-limiting algorithm.
 * 
 * <p>
 * The Token Bucket algorithm uses a metaphor of a bucket that can hold a
 * maximum number of tokens. Tokens are added to the bucket at a constant rate.
 * Each request consumes a certain number of tokens from the bucket. If there
 * are not enough tokens in the bucket, the request is delayed or rejected.
 * </p>
 */
public class TokenBucketRateLimiter implements RateLimiter {
	private final Map<String, TokenBucket> BUCKETS;

	public TokenBucketRateLimiter() {
		BUCKETS = new ConcurrentHashMap<>();
	}

	private TokenBucket getBucketMappedToThisResource(String resourceIdentifier) throws TokenBucketNotFoundException {
		TokenBucket bucket = BUCKETS.get(resourceIdentifier);
		if (bucket == null) {
			throw new TokenBucketNotFoundException("No bucket mapped to the resource identifier");
		}
		return bucket;
	}

	@Override
	public boolean allowAccess(String resourceIdentifier) throws TokenBucketNotFoundException {
		TokenBucket tokenBucket = getBucketMappedToThisResource(resourceIdentifier);
		return tokenBucket.tryConsume();
	}

	@Override
	public void reset(String resourceIdentifier) throws TokenBucketNotFoundException {
		TokenBucket tokenBucket = getBucketMappedToThisResource(resourceIdentifier);
		BUCKETS.put(resourceIdentifier, tokenBucket);

	}

	public void mapBucketToAResource(String resourceIdentifier, TokenBucket tokenBucket) {
		BUCKETS.put(resourceIdentifier, tokenBucket);
	}
}