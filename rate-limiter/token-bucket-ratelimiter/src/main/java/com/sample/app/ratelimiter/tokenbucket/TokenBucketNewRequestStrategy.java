package com.sample.app.ratelimiter.tokenbucket;

public enum TokenBucketNewRequestStrategy {
	/**
	 * When there are no new tokens, then the application delay the requests until the tokens are available
	 */
	DELAY, 
	
	/**
	 * Where there are no new tokens, then the application reject the request straight away
	 */
	REJECT

}
