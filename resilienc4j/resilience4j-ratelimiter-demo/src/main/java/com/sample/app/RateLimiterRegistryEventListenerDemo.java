package com.sample.app;

import java.time.Duration;

import io.github.resilience4j.ratelimiter.RateLimiter;
import io.github.resilience4j.ratelimiter.RateLimiterConfig;
import io.github.resilience4j.ratelimiter.RateLimiterRegistry;

public class RateLimiterRegistryEventListenerDemo {
	public static void main(String[] args) {

		RateLimiterConfig rateLimiterConfig = RateLimiterConfig.custom().limitForPeriod(2)
				.timeoutDuration(Duration.ofSeconds(4)).limitRefreshPeriod(Duration.ofSeconds(10)).build();
		RateLimiterRegistry rateLimiterRegistry = RateLimiterRegistry.of(rateLimiterConfig);

		rateLimiterRegistry.getEventPublisher().onEntryAdded(entryAddedEvent -> {
			RateLimiter addedRateLimiter = entryAddedEvent.getAddedEntry();
			System.out.println("RateLimiter added : " + addedRateLimiter.getName());
		}).onEntryRemoved(entryRemovedEvent -> {
			RateLimiter removedRateLimiter = entryRemovedEvent.getRemovedEntry();
			System.out.println("RateLimiter removed : " + removedRateLimiter.getName());
		}).onEntryReplaced(entryReplacedEvent -> {
			RateLimiter oldEntry = entryReplacedEvent.getOldEntry();
			RateLimiter newEntry = entryReplacedEvent.getNewEntry();
			System.out.println(oldEntry.getName() + " is replaced with " + newEntry.getName());
		});

		// Generate EntryAddedEvent
		rateLimiterRegistry.rateLimiter("myRateLimiter");

		// Generate EntryRemovedEvent
		rateLimiterRegistry.remove("myRateLimiter");

	}

}
