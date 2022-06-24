package com.sample.app;

import java.time.Duration;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.Cache.Entry;
import org.ehcache.config.CacheConfiguration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class SetExpiryAtCacheLevel {

	private static void printCacheElements(Cache<Long, String> empCache) {
		System.out.println("Elements in the cache are:");
		Iterator<Entry<Long, String>> iter = empCache.iterator();

		while (iter.hasNext()) {
			Entry<Long, String> entry = iter.next();
			System.out.printf("id : %d, data : %s\n", entry.getKey(), entry.getValue());
		}
	}

	public static void main(String[] args) throws InterruptedException {
		CacheConfiguration<Long, String> cacheConfiguration = CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(200))
				.withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(10))).build();

		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		Cache<Long, String> empCache = cacheManager.createCache("empCache", cacheConfiguration);
		empCache.put(1L, "Krishna, 23");
		empCache.put(2L, "Ram, 32");
		printCacheElements(empCache);

		System.out.println("\nSleep for 6 seconds\n");

		TimeUnit.SECONDS.sleep(6);

		printCacheElements(empCache);

		System.out.println("\nSleep for 6 seconds\n");

		TimeUnit.SECONDS.sleep(6);

		printCacheElements(empCache);

	}

}
