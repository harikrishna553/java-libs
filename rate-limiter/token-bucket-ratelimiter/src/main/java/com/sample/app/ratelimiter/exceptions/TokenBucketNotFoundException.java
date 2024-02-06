package com.sample.app.ratelimiter.exceptions;

public class TokenBucketNotFoundException extends RateLimiterException {

	public TokenBucketNotFoundException(Throwable t) {
		super(t);
	}

	public TokenBucketNotFoundException(String msg) {
		super(msg);
	}

}
