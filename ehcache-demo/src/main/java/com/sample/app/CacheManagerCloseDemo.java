package com.sample.app;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheManagerCloseDemo {

	public static void main(String[] args) {
		CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder().build(true);

		cacheManager.createCache("empCache", CacheConfigurationBuilder
				.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(100)).build());

		Cache<Long, String> empCache = cacheManager.getCache("empCache", Long.class, String.class);

		System.out.println("empCache : " + empCache);

		System.out.println("CacheManager status : " + cacheManager.getStatus());

		System.out.println("Closing the CacheManager");
		cacheManager.close();

		System.out.println("CacheManager status : " + cacheManager.getStatus());

		System.out.println("\n");

		try {
			empCache.put(1L, "test");
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("\n");
		try {
			// Throws java.lang.IllegalStateException
			empCache = cacheManager.getCache("empCache", Long.class, String.class);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
