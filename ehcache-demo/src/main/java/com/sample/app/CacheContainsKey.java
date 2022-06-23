package com.sample.app;

import java.util.HashSet;
import java.util.Set;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheContainsKey {

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		Cache<Long, String> empCache = cacheManager.createCache("empCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		empCache.put(1L, "Ram,34");
		empCache.put(2L, "Krishna,38");

		// Print entries in the cache
		Set<Long> keys = new HashSet<>();
		keys.add(1L);
		keys.add(2L);

		System.out.printf("empCache.containsKey(%d) = %b\n", 1l, empCache.containsKey(1l));
		System.out.printf("empCache.containsKey(%d) = %b\n", 2l, empCache.containsKey(2l));
		System.out.printf("empCache.containsKey(%d) = %b\n", 3l, empCache.containsKey(3l));

		cacheManager.close();
	}

}
