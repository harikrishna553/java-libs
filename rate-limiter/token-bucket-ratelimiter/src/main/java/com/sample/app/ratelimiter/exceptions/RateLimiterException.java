package com.sample.app.ratelimiter.exceptions;

public class RateLimiterException extends Exception {

	public RateLimiterException(Throwable t) {
		super(t);
	}
	
	public RateLimiterException(String msg) {
		super(msg);
	}

}
