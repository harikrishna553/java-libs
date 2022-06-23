package com.sample.app;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;

public class CacheRemoveOnlyWhenTheValueMatch {

	private static void printElementsFromCache(Cache<Long, String> empCache, Set<Long> keys) {
		System.out.println("Elements in the cache are:");
		Map<Long, String> map = empCache.getAll(keys);
		for (Long key : map.keySet()) {
			System.out.println(key + " is mapped to (" + map.get(key) + ")");
		}

	}

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

		printElementsFromCache(empCache, keys);

		System.out.println("\nTrying to Remove the element with key 2 when the value match to 'Joel,52'\n");
		empCache.remove(2l, "Joel,52");

		printElementsFromCache(empCache, keys);

		System.out.println("\nTrying to Remove the element with key 2 when the value match to 'Krishna,38'\n");
		empCache.remove(2l, "Krishna,38");

		printElementsFromCache(empCache, keys);

		cacheManager.close();
	}

}
